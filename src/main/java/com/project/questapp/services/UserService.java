package com.project.questapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Like;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.repos.PostRepository;
import org.springframework.stereotype.Service;

import com.project.questapp.entities.User;
import com.project.questapp.repos.UserRepository;

@Service
public class UserService {
	UserRepository userRepository;
	CommentRepository commentRepository;
	PostRepository postRepository;

	LikeRepository likeRepository;

	public UserService(UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository, LikeRepository likeRepository) {
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.likeRepository = likeRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	
	}

	public User saveOneUser(User newUser) {
		return userRepository.save(newUser);
	}

	public User getOneUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User updateOneUser(Long userId, User newUser) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			User foundUser = user.get();
			foundUser.setUserName(newUser.getUserName());
			foundUser.setPassword(newUser.getPassword());
			foundUser.setAvatar(newUser.getAvatar());
			userRepository.save(foundUser);
			return foundUser;
			
		}else
				return null;
	}

	public void deleteOneUser(Long userId) {
		userRepository.deleteById(userId);
	}

	public User getOneUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

    public List<Object> getUserActivity(Long userId) {
		List<Long> postIds = postRepository.findTopByUserId(userId);
		if(postIds.isEmpty())
			return null;
		List<Object> comments =commentRepository.findUserCommentByPostId(postIds);
		List<Object> likes =likeRepository.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;

    }
}
