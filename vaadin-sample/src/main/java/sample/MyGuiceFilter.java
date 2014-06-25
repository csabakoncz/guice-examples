package sample;

import javax.servlet.annotation.WebFilter;

import com.google.inject.servlet.GuiceFilter;

@WebFilter(urlPatterns = "/*")
public class MyGuiceFilter extends GuiceFilter {
}
