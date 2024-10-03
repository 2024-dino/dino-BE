package khu.dino.answer.business;

import khu.dino.answer.implement.AnswerCommandAdapter;
import khu.dino.answer.implement.AnswerQueryAdapter;
import khu.dino.answer.persistence.Answer;
import khu.dino.answer.persistence.enums.Type;
import khu.dino.answer.presentation.dto.AnswerRequestDto;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.common.util.AwsS3Util;
import khu.dino.common.util.MultipartFileUtil;
import khu.dino.event.implement.EventQueryAdapter;
import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.Member;
import khu.dino.question.implement.QuestionCommandAdapter;
import khu.dino.question.implement.QuestionQueryAdapter;
import khu.dino.question.persistence.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerCommandAdapter answerCommandAdapter;
    private final AnswerQueryAdapter answerQueryAdapter;
    private final QuestionCommandAdapter questionCommandAdapter;
    private final AwsS3Util awsS3Util;
    private final QuestionQueryAdapter questionQueryAdapter;
    private final EventQueryAdapter eventQueryAdapter;

    @Transactional(readOnly = false)
    public void saveAnswer(PrincipalDetails principalDetails, Long eventId, Long questionId, AnswerRequestDto.writeAnswerDto request,
                           MultipartFile mediaFile) throws Exception{
        Member member = principalDetails.getMember();
        Event event = eventQueryAdapter.findById(eventId);
        Question question = questionQueryAdapter.findById(questionId);

        questionCommandAdapter.setIsAnswered(question, true);
        Answer answer = saveAnswerInfo(member, question, request);
        if(mediaFile != null) {
            saveAnswerMedia(member, mediaFile, event.getId(), questionId, answer);
        }
    }

    @Transactional(readOnly = false)
    public Answer saveAnswerInfo(Member member, Question question, AnswerRequestDto.writeAnswerDto request) {
        return answerCommandAdapter.saveAnswer(AnswerMapper.toAnswer(member, question, request));
    }

    @Transactional(readOnly = false)
    public void saveAnswerMedia(Member member, MultipartFile mediaFile, Long eventId,Long questionId, Answer answer) throws Exception {
        Long answerId = answer.getId();
        String fileUrl = awsS3Util.uploadAnswerObject(member.getNickname(), mediaFile, eventId, questionId, answerId);
        Type type = MultipartFileUtil.getFileType(mediaFile);
        String fileName = mediaFile.getOriginalFilename();
        answer.updateFileUrl(fileUrl, fileName, type);
    }
}
