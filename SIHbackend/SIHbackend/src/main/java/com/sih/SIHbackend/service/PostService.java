package com.sih.SIHbackend.service;

import com.sih.SIHbackend.dto.request.PostRequest;
import com.sih.SIHbackend.dto.response.PostResponse;
import com.sih.SIHbackend.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest request);
    PostResponse getPostById(Long id);
    Page<PostResponse> getAllPosts(Pageable pageable);
    Page<PostResponse> getActivePosts(Pageable pageable);
    List<PostResponse> getExpiredPosts();
    Page<PostResponse> getPostsByStatus(PostStatus status, Pageable pageable);
    Page<PostResponse> getPostsByAuthor(String author, Pageable pageable);
    Page<PostResponse> searchPosts(String keyword, Pageable pageable);
    PostResponse updatePost(Long id, PostRequest request, String userEmail);
    PostResponse updatePostStatus(Long id, PostStatus status);
    void deletePost(Long id);
    int getCommentsCount(Long postId);
}
