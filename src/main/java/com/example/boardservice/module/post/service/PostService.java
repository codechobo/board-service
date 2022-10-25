package com.example.boardservice.module.post.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.category.repository.CategoryRepository;
import com.example.boardservice.module.hashtag.domain.repository.HashTagRepository;
import com.example.boardservice.module.like.domain.repository.LikeRepository;
import com.example.boardservice.module.member.domain.Member;
import com.example.boardservice.module.member.domain.repository.MemberRepository;
import com.example.boardservice.module.post.domain.Post;
import com.example.boardservice.module.post.domain.repository.PostRepository;
import com.example.boardservice.module.post.web.dto.request.RequestPostSaveDto;
import com.example.boardservice.module.post.web.dto.request.RequestPostUpdateDto;
import com.example.boardservice.module.post.web.dto.request.RequestSearchPostDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostListDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostPagingDto;
import com.example.boardservice.module.post.web.dto.response.ResponsePostSaveDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional
    public ResponsePostSaveDto savePost(RequestPostSaveDto requestDto) {
        Member member = getMemberEntity(requestDto.getAuthor());
        Post post = createPost(member.getNickname(), requestDto.getTitle(), requestDto.getContent());

        post.publish(); // 게시글 공개
        addCategory(requestDto, post); // 카테고리 추가

        Post savePost = postRepository.save(post);
        addHashTag(requestDto, savePost); // 해쉬태그 추가
        return ResponsePostSaveDto.of(savePost);
    }

    // TODO 중복되는 HashTag 는 어떻게 처리할지 고려할 것
    private void addHashTag(RequestPostSaveDto requestDto, Post post) {
        List<String> hashTagNames = requestDto.getHashTagNames();

        requestDto.getHashTagNames().forEach(
                hashTagNameData -> hashTagRepository.findByHashTagName(Strings.concat("#", hashTagNameData))
                        .ifPresent(hashTag -> post.getHashTags().add(hashTag))
        );
    }

    private void addCategory(RequestPostSaveDto requestDto, Post post) {
        categoryRepository.findById(requestDto.getCategoryId()).ifPresent(post::addCategory);
    }


    private Member getMemberEntity(String author) {
        return memberRepository.findByNickname(author)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    @Transactional
    public ResponsePostSaveDto findByPostId(Long postId) {
        Post post = getPostEntity(postId);
        post.checkPublished();
        post.updateViewCount();

        long hits = getPostLikes(post);
        post.updateLikes(hits);
        return ResponsePostSaveDto.of(post);
    }

    private long getPostLikes(Post post) {
        return likeRepository.countByPostsId(post.getId());
    }


    @Transactional
    public void updateAfterFindPost(Long postId, RequestPostUpdateDto requestDto) {
        Post post = getPostEntity(postId);
        post.updatePost(requestDto.getTitle(), requestDto.getContent());
    }

    @Transactional
    public void removePost(Long postId) {
        Post post = getPostEntity(postId);
        postRepository.delete(post);
    }

    public ResponsePostPagingDto findPosts(RequestSearchPostDto requestSearchPostDto, Pageable pageable) {
        Page<ResponsePostListDto> responsePostListDto = postRepository.getMembersIncludingLastCreate(
                requestSearchPostDto.getAuthor(),
                requestSearchPostDto.getTitle(),
                requestSearchPostDto.getContent(),
                pageable);

        return ResponsePostPagingDto.toMapper(responsePostListDto);
    }

    public ResponsePostPagingDto findPostsFromViewCount(RequestSearchPostDto requestSearchPostDto, Pageable pageable) {
        Page<ResponsePostListDto> responsePostListDto = postRepository.getPostViewCountSort(
                requestSearchPostDto.getAuthor(),
                requestSearchPostDto.getTitle(),
                requestSearchPostDto.getContent(),
                pageable);

        return ResponsePostPagingDto.toMapper(responsePostListDto);
    }

    private Post getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    private Post createPost(String author, String title, String content) {
        return Post.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();
    }

    @Transactional
    public void close(Long postId) {
        Post post = getPostEntity(postId);
        post.close();
    }
}
