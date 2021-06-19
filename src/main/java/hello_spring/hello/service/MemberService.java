package hello_spring.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello_spring.hello.domain.Member;
import hello_spring.hello.repository.MemberRepository;

//@Service // 스프링 컨테이너에 자동으로 서비스를 등록해준다.
public class MemberService {
    private final MemberRepository memberRepository;

    // @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 같은 이름의 중복회원 차단.
        // 값이 있으면 꺼내고, 없으면 메서드 실행하라.
        // Optional<Member> result = memberRepository.findByName(member.getName());
        // result.ifPresent(m -> {
        // throw new IllegalStateException("이미 존재하는 회원입니다.");
        // });

        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);// 중복회원 검증잉 끝나면 저장
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
