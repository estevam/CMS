package ca.est.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import ca.est.entity.Article;
import ca.est.entity.UserCMS;
import ca.est.entity.http.ArticleCreateRequest;
import ca.est.entity.http.ArticlePaginationRequest;
import ca.est.entity.http.ArticleRequest;
import ca.est.entity.http.ArticleResponse;
import ca.est.entity.http.ServiceResponse;
import ca.est.exception.NoSuchElementFoundException;
import ca.est.repository.ArticlePagingAndSortingRepository;
import ca.est.repository.ArticleRepository;
import ca.est.security.UserDetailsBuilder;

/**
 * @author Estevam Meneses
 */
@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticlePagingAndSortingRepository articlePagingAndSortingRepository;

	@Autowired
	private ModelMapper modelMapper;

	//@Autowired
	//private RestUtil restUtil;

	/**
	 * Find all articles
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse findAllArticles() throws NoSuchElementFoundException {
		List<Article> articleList = articleRepository.findAll();
		TypeToken<List<ArticleResponse>> typeToken = new TypeToken<>() {
		};
		List<ArticleResponse> userResponList = modelMapper.map(articleList, typeToken.getType());
		return new ServiceResponse(userResponList, HttpStatus.OK);
	}

	/**
	 * Find all articles
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse findAllArticlesByPagination(ArticlePaginationRequest articlePaginationRequest)
			throws NoSuchElementFoundException {
		
		Pageable pagable = PageRequest.of(articlePaginationRequest.getPage_index(), articlePaginationRequest.getPage_size());
		Page<Article> articleList = articlePagingAndSortingRepository.findAll(pagable);

		TypeToken<List<ArticleResponse>> typeToken = new TypeToken<>() {
		};
		List<ArticleResponse> userResponList = modelMapper.map(articleList, typeToken.getType());

		return new ServiceResponse(userResponList, HttpStatus.OK);
	}

	/**
	 * Find article
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse findArticle(Long id) throws NoSuchElementFoundException {
		if (id == null) {
			return new ServiceResponse(HttpStatus.BAD_REQUEST);
		}
		Optional<Article> article = articleRepository.findById(1L);
		if (article.isPresent()) {
			ArticleResponse articleResponse = modelMapper.map(article.get(), ArticleResponse.class);
			return new ServiceResponse(articleResponse, HttpStatus.OK);
		} else {
			return new ServiceResponse(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 *
	 * Update userBlog
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse updateArticle(ArticleRequest userUpdateRequest) throws NoSuchElementFoundException {
		Optional<Article> article = articleRepository.findById(userUpdateRequest.getId_article());
		if (article.isPresent()) {
			Article ar = article.get();
			ar.setStatus(1);
			ar.setTitle(userUpdateRequest.getTitle());
			ar.setText(userUpdateRequest.getText());

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object auth = authentication.getPrincipal();
			UserDetailsBuilder secUser = (UserDetailsBuilder) auth;
			TypeToken<UserCMS> typeToken = new TypeToken<>() {
			};
			UserCMS userCMS = modelMapper.map(secUser, typeToken.getType());
			ar.setUserCMS(userCMS);

			articleRepository.save(ar);
			return new ServiceResponse(HttpStatus.OK);
		} else {
			return new ServiceResponse(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 *
	 * Create userBlog
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse createArticle(ArticleCreateRequest articleCreateRequest) throws NoSuchElementFoundException {

		TypeToken<UserCMS> typeToken = new TypeToken<>() {
		};
		Article article = modelMapper.map(articleCreateRequest, typeToken.getType());
		try {
			article.setCreated(LocalDateTime.now());
			articleRepository.save(article);
			return new ServiceResponse(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ServiceResponse(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse deleteArticle(Long id) {
		Optional<Article> article = articleRepository.findById(id);
		if (article.isPresent()) {
			Article ar = article.get();
			articleRepository.delete(ar);
			return new ServiceResponse(HttpStatus.OK);
		} else {
			return new ServiceResponse(HttpStatus.NOT_FOUND);
		}
	}

}
