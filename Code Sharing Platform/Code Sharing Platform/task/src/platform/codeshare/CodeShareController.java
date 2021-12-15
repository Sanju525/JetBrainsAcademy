package platform.codeshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CodeShareController {

    @Autowired
    CodeShareService codeService;

    // code/new -> GET FormView
    @GetMapping("/code/new")
    public String getForm() {
        return codeService.getForm();
    }

    // code/{id} -> Get the code with the given id
    @GetMapping("/code/{uuid}")
    public String getCodeById(@PathVariable String uuid, Model model) {
        return codeService.getCodeById(uuid, model);
    }

    // code/latest -> Get the top 10 sorted codes.
    @GetMapping("/code/latest")
    public String getLatestCode(Model model) {
        return codeService.getLatestCode(model);
    }

//    -------------------------------------------API-------------------------------------------

    // api/code/new -> RequestBody from user :: Returns {"id": id}
    @PostMapping("/api/code/new")
    @ResponseBody
    public Object postNewCode(@RequestBody Code code) {
        return codeService.postNewCode(code);
    }

    // api/code/latest
    @GetMapping("/api/code/latest")
    @ResponseBody
    public Object getLatestApiCode() {
        return codeService.getLatestApiCode();
    }

    // api/code/{id}
    @GetMapping("/api/code/{uuid}")
    @ResponseBody
    public Object getApiCodeById(@PathVariable String uuid) {
        return codeService.getApiCodeById(uuid);
    }

}