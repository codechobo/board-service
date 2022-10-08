package com.example.boardservice.module.post.service;

import com.example.boardservice.error.ErrorCode;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public ResponsePostSaveDto savePost(RequestPostSaveDto requestDto) {
        Member member = getMemberEntity(requestDto.getAuthor());

        Post post = createPost(member.getNickname(), requestDto.getTitle(), requestDto.getContent());
        post.publish();

        Post savePost = postRepository.save(post);
        return ResponsePostSaveDto.of(savePost);
    }

    private Member getMemberEntity(String author) {
        return memberRepository.findByNickname(author)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    // 공개 여부에 따라 조회
    public ResponsePostSaveDto findByPostId(Long postId) {
        Post post = getPostEntity(postId);
        post.checkPublished();
        return ResponsePostSaveDto.of(post);
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
