package com.danggeun.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.danggeun.mail.exception.EmailDuplicatedException;
import com.danggeun.mail.exception.NoCertificationException;
import com.danggeun.region.dto.RegionDTO;
import com.danggeun.region.repository.RegionRepository;
import com.danggeun.user.dto.UserDTO;
import com.danggeun.user.exception.NicknameDuplicatedException;
import com.danggeun.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final RegionRepository regionRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 사용자 생성
	 * @param userDTO
	 * @return UserDTO
	 */
	public UserDTO join(UserDTO userDTO) {
		// 사용자 가입 필수 데이터 중 null 값이 있는지, 비밀번호 형식이 맞는지 체크
		userDTO.validate();

		// 클라이언트에서 사용자 이메일 중복체크를 수행하지만 API요청에 의한 예외상황에 대비하여 더블체크
		isDuplicatedEmail(userDTO.getUserEmail());

		// 클라이언트에서 사용자 닉네임 중복체크를 수행하지만 API요청에 의한 예외상황에 대비하여 더블체크
		isDuplicatedNickname(userDTO.getUserNickname());

		// 이메일 인증 체크
		validateCertificationUser(userDTO.isUserEmailCertificationFlag());

		// 비밀번호 암호화
		String encodePw = passwordEncoder.encode(userDTO.getUserPassword());
		userDTO.setUserPassword(encodePw);
		UserDTO result = userRepository.save(userDTO);

		// 사용자 지역 정보 생성
		UserDTO userRegionDTO = userRepository.findByEmail(result.getUserEmail()).get();

		RegionDTO regionDTO = new RegionDTO();
		regionDTO.setUserId(userRegionDTO.getUserId());
		regionDTO.setLongitude(userDTO.getLongitude());
		regionDTO.setLatitude(userDTO.getLatitude());
		regionDTO.setRepresentRegionStatus(userDTO.getRepresentRegionStatus());
		regionDTO.setRegionRangeStatus(userDTO.getRegionRangeStatus());
		regionDTO.setUserActive(userRegionDTO.isUserActive());
		regionDTO.setCreatedId(userRegionDTO.getCreateId());
		regionDTO.setModifiedId(userRegionDTO.getModifiedId());
		regionRepository.save(regionDTO);

		return result;
	}

	/**
	 * 이메일 중복 사용자 체크
	 * @param email
	 */
	public void isDuplicatedEmail(String email) {
		userRepository.findByEmail(email).ifPresent(m -> {
			throw new EmailDuplicatedException("이미 가입한 유저입니다.");
		});
	}

	/**
	 * 닉네임 중복 사용자 체크
	 * @param nickname
	 */
	public void isDuplicatedNickname(String nickname) {
		userRepository.findByNickname(nickname).ifPresent(m -> {
			throw new NicknameDuplicatedException("닉네임이 중복됩니다.");
		});
	}

	/**
	 * 인증 유무 확인
	 * @param certificationFlag
	 */
	public void validateCertificationUser(boolean certificationFlag) {
		if (!certificationFlag) {
			throw new NoCertificationException("인증하지 않았습니다.");
		}
	}
}