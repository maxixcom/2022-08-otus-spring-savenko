package io.github.maxixcom.otus.quiz.service.logging;

import io.github.maxixcom.otus.quiz.domain.Question;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class DaoLoggerService {
    private static final Logger logger = LoggerFactory.getLogger(DaoLoggerService.class);

    @AfterReturning(pointcut = "execution(* io.github.maxixcom.otus.quiz.dao.QuestionLoader.load())", returning = "list")
    List<Question> logLoaderLoad(List<Question> list) {
        logger.info("Loaded {} questions", list.size());
        return list;
    }


}
