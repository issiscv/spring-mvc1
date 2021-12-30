package mvc.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member hello = new Member("hello", 20);

        Member savedMember = memberRepository.save(hello);

        Member findMember = memberRepository.findById(savedMember.getId());

        assertThat(savedMember).isEqualTo(findMember);
    }
    
    @Test
    void findAll() {
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 20);

        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);

        List<Member> findAll = memberRepository.findAll();

        assertThat(findAll.size()).isEqualTo(2);
        assertThat(findAll).contains(savedMember1, savedMember2);

    }
}