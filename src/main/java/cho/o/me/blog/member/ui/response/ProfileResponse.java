package cho.o.me.blog.member.ui.response;

import cho.o.me.blog.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileResponse {

    String username;
    String bio;
    String image;
    Boolean following;

    public ProfileResponse(Member member, boolean exist) {
        username = member.getUsername();
        bio = member.getBio();
        image = member.getImage();
        following = exist;
    }

    public ProfileResponse(Member member) {
        username = member.getUsername();
        bio = member.getBio();
        image = member.getImage();
        following = false;
    }
}
