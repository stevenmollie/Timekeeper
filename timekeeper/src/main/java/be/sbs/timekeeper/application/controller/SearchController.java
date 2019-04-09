package be.sbs.timekeeper.application.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import be.sbs.timekeeper.application.beans.SearchResult;
import be.sbs.timekeeper.application.service.SearchService;

import org.springframework.http.HttpStatus;

@RestController
public class SearchController {
	private final SearchService searchService;
	
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}
	
	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public SearchResult search(@RequestParam String keyword){
		return searchService.getSearchResult(keyword);
	}
}
