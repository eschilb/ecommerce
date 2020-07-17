package com.tts.TechTalentEcommerce.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.TechTalentEcommerce.Model.Product;
import com.tts.TechTalentEcommerce.Service.ProductService;


@Controller
@ControllerAdvice // This makes the `@ModelAttribute`s of this controller available to all controllers, for the navbar.
public class MainController {
    @Autowired
    ProductService productService;
    

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @ModelAttribute("products")
    public List<Product> products() {
    	if (productService.findAll().isEmpty()) {
    		//this is a patch to the heroku database problem, ideally the database persists
    		productService.save(new Product(1, 45, "Wears like a modern 29/30 depending on the fit you're looking for, Made in Colombia, has the paper tag so I'd date these as 90s 100% cotton Button fly Previous owner customized these 501s into cutoffs with a raw hem", "501 Denim Cutoff Shorts", "Levi's", "Denim", "/image/5/levis501cutoffs.jpg"));
    		productService.save(new Product(1, 40, "Heavily Distressed Size Medium, split collar and hem, sleeves cut to short sleeve, paint all over the front, soft vintage feel, extremely worn", "U.S. Army Sweatshirt", "Vintage", "Sweatshirt", "/image/6/ftleesweatshirt.jpg"));
    		productService.save(new Product(1, 65, "Rare, distressed at the collar, some stains in the front, heathered grey color with red and white and grey stripe at sleeve cuffs, collar, and bottom hem, red Enjoy Diet Coke graphic", "Diet Coke Sweatshirt", "Vintage", "Sweatshirt", "/image/1/dietcokesweatshirt.jpg"));
    			productService.save(new Product(1, 45, "Sized like a modern 28/29 100% cotton material, made in USA, Zipper fly, Customized by the previous owner into cutoffs and features a raw hem", "517 Denim Cutoff Shorts", "Levi's", "Denim", "/image/2/levis517cutoffs.jpg"));
    	}
        return productService.findAll();
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return productService.findDistinctCategories();
    }

    @ModelAttribute("brands")
    public List<String> brands() {
        return productService.findDistinctBrands();
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String category,
                         @RequestParam(required = false) String brand,
                         Model model) {
        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
        model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}


