package cn.com.xuroot.service;

import cn.com.xuroot.bo.StudentAnswersBo;
import cn.com.xuroot.entity.StudentAnswers;

public interface StudentAnswersService {
    int addStudentAnswers(StudentAnswersBo studentAnswersBo);
    boolean checkAnswerRecord(Integer studentId, Integer examId);
}
