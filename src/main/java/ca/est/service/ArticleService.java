package ca.est.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ca.est.entity.Article;
import ca.est.entity.http.ServiceResponse;
import ca.est.exception.NoSuchElementFoundException;
import ca.est.repository.ArticleRepository;

/**
 * @author Estevam Meneses
 */
@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	
    /**
     * Find all articles
     * @return
     */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ServiceResponse findAllArticles() throws NoSuchElementFoundException{
    	List<Article> articlesList = articleRepository.findAll();
    	if(articlesList.isEmpty()) {
        	return new ServiceResponse(HttpStatus.BAD_REQUEST);
    	}
    	return new ServiceResponse(articlesList, HttpStatus.OK);
    }

}
