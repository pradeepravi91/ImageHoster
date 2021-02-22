package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Comment;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    //This controller method is called when the request pattern is of type '/image/{imageId}/{imageTitle}/comments'
    //The method returns '/image/{imageId}/{imageTitle}/comments'
    @RequestMapping(value = "/image/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String createComment(@RequestParam("comment") String text, @PathVariable("imageId") Integer imageId, @PathVariable("title") String title, HttpSession session) {
        User user = (User)session.getAttribute("loggeduser");
        Comment newComment = new Comment();
        newComment.setUser(user);
        newComment.setImage(imageService.getImage(imageId));
        newComment.setText(text);
        newComment.setCreatedDate(LocalDate.now());
        commentService.uploadComment(newComment);

        return "redirect:/images/" + imageId + "/" + title;
    }
}