package cho.o.me.blog.article.application;

import cho.o.me.blog.account.application.AccountUsercase;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.ui.request.ArticleCreateRequest;
import cho.o.me.blog.article.ui.request.ArticleFetchRequest;
import cho.o.me.blog.article.ui.response.ArticleListResponse;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import cho.o.me.blog.article.ui.response.ArticleCreateResponse;
import cho.o.me.blog.favorite.application.FavoriteService;
import cho.o.me.blog.favorite.domain.Favorite;
import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.application.MemberProfileService;
import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import cho.o.me.blog.tag.application.TagService;
import cho.o.me.blog.tag.domain.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleUsecase {

    private final ArticleService articleService;
    private final FavoriteService favoriteService;
    private final AccountUsercase accountUsercase;
    private final MemberService memberService;
    private final FollowService followService;
    private final TagService tagService;
    private final MemberProfileService memberProfileService;

    public ArticleCreateResponse create(String email, ArticleCreateRequest request) {
        Member member = memberService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " is not found"));

        Article article = Article.builder()
                .slug(slug(request.getTitle()))
                .title(request.getTitle())
                .description(request.getDescription())
                .body(request.getBody())
                .tagList(tagIds(request.getTagNameList()))
                .createdAt(Date.valueOf(LocalDate.now()))
                .author(member.getUsername())
                .build();

        Article save = articleService.save(article);

        return ArticleCreateResponse.of(
                save.getTitle(),
                save.getSlug(),
                save.getDescription(),
                save.getBody(),
                tagService.findAllById(save.getTagList())
        );
    }

    private List<UUID> tagIds(List<String> tagNames) {
        List<Tag> tagList = tagService.findAllByNameIn(tagNames);

        if (tagNames.size() == tagList.size()) {
            return tagList.stream().map(Tag::getId).toList();
        } else {
            return newTagIds(tagNames, tagList);
        }

    }

    private List<UUID> newTagIds(List<String> tagNameList, List<Tag> tagList) {
        List<UUID> tagListUUIDs = tagList.stream().map(Tag::getId).collect(Collectors.toList());
        Set<String> tagListNames = tagList.stream().map(Tag::getName).collect(Collectors.toSet());
        List<Tag> newTags = new ArrayList<>();

        tagNameList.forEach(tagName -> {
            if (!tagListNames.contains(tagName)) {
                Tag tag = new Tag(tagName);
                newTags.add(tag);
            }
        });

        tagService.saveAll(newTags).forEach(tag -> {
            tagListUUIDs.add(tag.getId());
        });

        return tagListUUIDs;
    }

    private List<String> tagNames(List<Tag> tagList){
        return tagList.stream().map(Tag::getName).collect(Collectors.toList());
    }
    private String slug(String title) {
        return title.replace(' ', '_');
    }

    @Transactional
    public ArticleResponse article(String slug) {
        Article article = articleService.findBySlug(slug);

        return ArticleResponse.of(
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                tagService.findAllById(article.getTagList()),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                false,
                0,
                article.getAuthor());
    }

    @Transactional
    public ArticleResponse favorite(String email, String slug) {
        AccountResponse accountResponse = accountUsercase.user(email);
        Article article = articleService.findBySlug(slug);
        Favorite favoriteRequest = Favorite.builder().userEmail(accountResponse.email()).slug(article.getSlug()).build();

        favoriteService.save(favoriteRequest);
        List<Favorite> favoriteList = favoriteService.findBySlug(slug);

        ProfileResponse profile = memberProfileService.profile(article.getAuthor());

        return ArticleResponse.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .tagNameList(tagNames(tagService.findAllById(article.getTagList())))
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(true)
                .favoritesCount(favoriteList.size())
                .profile(profile)
                .build();
    }

    @Transactional
    public ArticleResponse unfavorite(String email, String slug) {
        AccountResponse accountResponse = accountUsercase.user(email);
        Article article = articleService.findBySlug(slug);
        Favorite favorite = favoriteService.findByUseremailAndSlug(accountResponse.email(), article.getSlug());

        favoriteService.delete(favorite);
        List<Favorite> favoriteList = favoriteService.findBySlug(slug);

        ProfileResponse profile = memberProfileService.profile(article.getAuthor());

        return ArticleResponse.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .tagNameList(tagNames(tagService.findAllById(article.getTagList())))
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(false)
                .favoritesCount(favoriteList.size())
                .profile(profile)
                .build();
    }

    public ArticleListResponse articles(Pageable pageable, ArticleFetchRequest request, String email) {
        List<Article> articleList = articleService.articles(pageable, request);
        AccountResponse accountResponse = accountUsercase.user(email);
        return null;
    }
}
