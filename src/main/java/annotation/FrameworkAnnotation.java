package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import enums.AuthorType;
import enums.CategoryType;



@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FrameworkAnnotation {
	
	
	  AuthorType[] author() default { AuthorType.ROHIT };;
	    CategoryType[] category()default { CategoryType.SANITY, CategoryType.SMOKE, CategoryType.REGRESSION };
	

}
