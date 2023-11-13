package iium.jjs.sansang_back.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import iium.jjs.sansang_back.exception.TokenException;
import iium.jjs.sansang_back.exception.dto.ApiExceptionDTO;
import iium.jjs.sansang_back.exception.dto.RefreshTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class FilterExceptionFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    try{
      filterChain.doFilter(request, response);
    }catch (TokenException | RefreshTokenException e){
      setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
    }

  }

  public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {

    response.sendError(status.value(), ex.getMessage());


  }

}
