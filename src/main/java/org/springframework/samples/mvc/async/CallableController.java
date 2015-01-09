package org.springframework.samples.mvc.async;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;
@Controller
@RequestMapping("/async/callable")
public class CallableController {
	public static final int WAIT =2000,CALLABLE=1000;
	
	@RequestMapping("/response-body")
	 @ResponseBody public Callable<String> callable() {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(WAIT);
				return "Callable result";
			}
		};
	}
	@RequestMapping("/view")
	public Callable<String> callableWithView(final Model model) {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(WAIT);
				model.addAttribute("foo", "bar");
				model.addAttribute("fruit", "apple");
				return "views/html";
			}
		};
	}
	@RequestMapping("/exception")
	 @ResponseBody public Callable<String> callableWithException(
			final @RequestParam(required=false, defaultValue="true") boolean handled) {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(WAIT);
				if (handled) {
					// see handleException method further below
					throw new IllegalStateException("Callable error");
				}else
				{
					throw new IllegalArgumentException("Callable error");
				}
			}
		};
	}
	@RequestMapping("/custom-timeout-handling")
	 @ResponseBody public WebAsyncTask<String> callableWithCustomTimeoutHandling() {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(WAIT);
				return "Callable result";
			}
		};
		return new WebAsyncTask<String>(CALLABLE, callable);
	}
	@ExceptionHandler
	@ResponseBody
	public String handleException(IllegalStateException ex) {
		return "Handled exception: " + ex.getMessage();
	}
}
