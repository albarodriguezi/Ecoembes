/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.auctions.dto.ArticleDTO;
import es.deusto.sd.auctions.dto.CategoryDTO;
import es.deusto.sd.auctions.entity.Article;
import es.deusto.sd.auctions.entity.Category;
import es.deusto.sd.auctions.entity.Employee;
import es.deusto.sd.auctions.entity.User;
import es.deusto.sd.auctions.service.AuctionsService;
import es.deusto.sd.auctions.service.AuthService;
import es.deusto.sd.auctions.service.CurrencyService;
import es.deusto.sd.auctions.service.DumpsterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auctions")
@Tag(name = "Auctions Controller", description = "Operations related to categories, articles and bids")
public class AuctionsController {

	private final AuctionsService auctionsService;
	private final AuthService authService;
	private final DumpsterService dumpsterService;

	public AuctionsController(AuctionsService auctionsService, AuthService authService, DumpsterService dumpsterService) {
		this.auctionsService = auctionsService;
		this.authService = authService;
		this.dumpsterService = dumpsterService;
	}

	// GET all categories
	@Operation(
		summary = "Get all categories",
		description = "Returns a list of all available categories",
		responses = {
			@ApiResponse(responseCode = "200", description = "OK: List of categories retrieved successfully"),
			@ApiResponse(responseCode = "204", description = "No Content: No Categories found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
		}
	)
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		try {
			List<Category> categories = auctionsService.getCategories();
			
			if (categories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			List<CategoryDTO> dtos = new ArrayList<>();
			categories.forEach(category -> dtos.add(categoryToDTO(category)));
			
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// GET articles by category name
	/* 
	@Operation(
		summary = "Get articles by category name",
		description = "Returns a list of all articles for a given category",
		responses = {
			@ApiResponse(responseCode = "200", description = "OK: List of articles retrieved successfully"),
			@ApiResponse(responseCode = "204", description = "No Content: Category has no articles"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Currency not supported"),
			@ApiResponse(responseCode = "404", description = "Not Found: Category not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
		}
	)
	
	@GetMapping("/categories/{categoryName}/articles")
	public ResponseEntity<List<ArticleDTO>> getArticlesByCategory(
			@Parameter(name = "categoryName", description = "Name of the category", required = true, example = "Electronics")
			@PathVariable("categoryName") String category,
			@Parameter(name = "currency", description = "Currency", required = true, example = "GBP")
			@RequestParam("currency") String currentCurrency) {
		try {
			List<Article> articles = auctionsService.getArticlesByCategoryName(category);
						
			if (articles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			Optional<Float> exchangeRate = currencyService.getExchangeRate(currentCurrency);
			
			if (!exchangeRate.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
			
			List<ArticleDTO> dtos = new ArrayList<>();
			articles.forEach(article -> dtos.add(articleToDTO(article, exchangeRate.get(), currentCurrency)));
			
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	*/
	// GET details of an article by ID
	@Operation(
		summary = "Get the details of an article by its ID",
		description = "Returns the details of the article with the specified ID",
		responses = {
			@ApiResponse(responseCode = "200", description = "OK: Article details retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Currency not supported"),
			@ApiResponse(responseCode = "404", description = "Not Found: Article not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
		}
	)
	 /*
	@GetMapping("/articles/{articleId}/details")
	public ResponseEntity<ArticleDTO> getArticleDetails(
			@Parameter(name = "articleId", description = "Id of the article", required = true, example = "1")
			@PathVariable("articleId") long id,
			@Parameter(name = "currency", description = "Currency", required = true, example = "EUR")
			@RequestParam("currency") String currentCurrency) {
		try {
			Article article = auctionsService.getArticleById(id);			
			
			if (article != null) {				
				Optional<Float> exchangeRate = currencyService.getExchangeRate(currentCurrency);
				
				if (!exchangeRate.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
				
				ArticleDTO dto = articleToDTO(article, exchangeRate.get(), currentCurrency);
				
				return new ResponseEntity<>(dto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// POST to make a bid on an article
	@Operation(
	    summary = "Create a dumpster",
	    description = "Allow an employee to crate a dumpster by providing its location and capacity.",
	    responses = {
	        @ApiResponse(responseCode = "204", description = "No Content: dumpster created successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Error in the request parameters"),
	        @ApiResponse(responseCode = "401", description = "Unauthorized: Employee not authenticated"),
	        @ApiResponse(responseCode = "404", description = "Not Found: Dumpster not found"),
	        @ApiResponse(responseCode = "409", description = "Conflict: Conflict with the current state of the resource"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")
	    }
	)		
	@PostMapping("/dumpster/{dumpsterId}")
	public ResponseEntity<Void> createDumpster(
			@Parameter(name = "dumpster", description = "ID of the dumpster", required = true, example = "1")		
			@PathVariable("dumpsterID") long id,
			@Parameter(name = "PC", description = "postal code of the dumpster", required = true, example = "10001")
    		@RequestParam("dumpsterPC") int pc,
    		@Parameter(name = "city", description = "city of the dumpster", required = true, example = "pamplona")
			@RequestParam("dumpsterCity") String dumpsterCity,
			@Parameter(name = "address", description = "address of the dumpster", required = true, example = "pio XII")
			@RequestParam("dumpsterAddress") String dumpsterAddress,
			@Parameter(name = "type", description = "type of the dumpster", required = true, example = "paper")
			@RequestParam("dumpsterType") String dumpsterTy,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authorization token in plain text", required = true)
    		@RequestBody String token) { 
	    try {	    	
	    	Employee user = authService.getUserByToken(token);
	    	
	    	if (user == null) {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	}
	    	
			
			// If the currency is not EUR, convert the amount to EUR
			
	        dumpsterService.createDumpster(id, pc, dumpsterCity, dumpsterAddress, dumpsterTy,token);
	        
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	        switch (e.getMessage()) {
	            case "Article not found":
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            case "Bid amount must be greater than the current price":
	                return new ResponseEntity<>(HttpStatus.CONFLICT);
	            default:
	                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	}
*/
	// Converts a Category to a CategoryDTO
	private CategoryDTO categoryToDTO(Category category) {
		return new CategoryDTO(category.getName());
	}
	
	// Converts an Article to an ArticleDTO
	private ArticleDTO articleToDTO(Article article, float exchangeRate, String currency) {
		return new ArticleDTO(article.getId(), 
				              article.getTitle(), 
				              article.getInitialPrice() * exchangeRate,
				              article.getCurrentPrice() * exchangeRate,
				              article.getBids().size(),
				              article.getAuctionEnd(),
				              article.getCategory().getName(), 
				              article.getOwner().getNickname(),
				              currency);
	}
}