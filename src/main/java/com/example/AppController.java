package com.example;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository repo;
	
	 @Autowired
	    private ProgramRepository programRepo;
	 
	  @Autowired
	    private ApplicationRepository applicationRepo;
	  
	  @Autowired
	    private ApplicationService applicationService;
	  
	
	  
	  
	  @Autowired
	  private DroolsService droolsService;

	
	@GetMapping("")
	public String viewHopePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		
		return"signup_form";
	}
	
	@GetMapping("/admin")
	public String showAdminPage(Model model) {
		 List<Program> programs = programRepo.findAll();
		    model.addAttribute("programs", programs);
		
		return"admin_page";
	}
	@PostMapping("/process_register")
	public String processRegistration(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		 user.setRole("ROLE_USER");
		
		 // Ενημέρωση με τα νέα πεδία
	    Integer degreeScore = user.getDegreeScore();
	    Double income = user.getIncome();
	    String city = user.getCity();
	    Boolean remote = user.getRemote();
	    
	    
	    
	    // Εύκολη επικύρωση για βαθμό πτυχίου
	    if (degreeScore != null && (degreeScore < 1 || degreeScore > 9)) {
	        return "error"; // ή δείξε μήνυμα σφάλματος στον χρήστη
	    }
		
		
		repo.save(user);
		
		return"register_sucess";
	}
	
	@GetMapping("/users")
	public String viewUsersList(Model model) {
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers",listUsers);
		
		  List<Program> listPrograms = programRepo.findAll();
	        model.addAttribute("listPrograms", listPrograms);
	        
	       
		return"users";
	}
	
	@GetMapping("/add_program")
    public String showAddProgramForm(Model model) {
        model.addAttribute("program", new Program());
        return "add_program";
    }
	
	@PostMapping("/process_add_program")
    public String addProgram(Program program) {
        programRepo.save(program);
        return "program_added_success";
    }
	
	@GetMapping("/list_programs")
    public String listPrograms(Model model) {
        List<Program> listPrograms = programRepo.findAll();
        model.addAttribute("listPrograms", listPrograms);
        return "list_programs";
    }
	
	@PostMapping("/select_program")
    public String selectProgram(@RequestParam("programId") Long programId) {
        // Πάρε το Authentication αντικείμενο
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Αυτό θα πάρει το όνομα χρήστη

        // Βρες τον χρήστη με βάση το email
        User user = repo.findByEmail(username); // Προσάρμοσε αυτή τη μέθοδο στο UserRepository
        
        // Βρες το πρόγραμμα που επιλέχθηκε
        Program program = programRepo.findById(programId).orElseThrow(() -> new RuntimeException("Program not found"));
        
        // Πρόσθεσε το πρόγραμμα στον χρήστη
        List<Program> userPrograms = user.getPrograms();
        if (!userPrograms.contains(program)) {
            userPrograms.add(program);
            user.setPrograms(userPrograms);
            repo.save(user);
        }

        return "program_added_success1"; // Επανέφερε στη σελίδα "home" ή αλλού αν χρειάζεται
    }
	
	@GetMapping("/my_programs")
	public String viewMyPrograms(Model model) {
	    // Πάρε το Authentication αντικείμενο
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName(); // Αυτό θα πάρει το όνομα χρήστη
	    
	    // Βρες τον χρήστη με βάση το email
	    User user = repo.findByEmail(username); // Προσάρμοσε αυτή τη μέθοδο στο UserRepository

	    if (user != null) {
	        // Πάρε τα προγράμματα που έχει επιλέξει ο χρήστης
	        List<Program> userPrograms = user.getPrograms();
	        model.addAttribute("userPrograms", userPrograms);
	    } else {
	        model.addAttribute("error", "User not found");
	    }
	    
	    return "my_programs"; // Επιστροφή στην αντίστοιχη σελίδα JSP ή HTML
	}
	
	@GetMapping("/programs_with_users")
	public String viewProgramsWithUsers(Model model) {
	    List<Program> programs = programRepo.findAll();
	    model.addAttribute("programs", programs);
	    return "programs_with_users";
	}
	
	 @GetMapping("/apply_program")
	    public String showApplyProgramForm(@RequestParam("programId") Long programId, Model model) {
	        Program program = programRepo.findById(programId).orElseThrow(() -> new RuntimeException("Program not found"));
	        model.addAttribute("program", program);
	        return "apply_program";
	    }
	 
	 @PostMapping("/process_application")
	    public String processApplication(@RequestParam("programId") Long programId,
	                                     @RequestParam("degreeScore") Integer degreeScore,
	                                     @RequestParam("income") Double income,
	                                     @RequestParam("city") String city,
	                                     @RequestParam("remote") Boolean remote) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        User user = repo.findByEmail(username);

	        Program program = programRepo.findById(programId).orElseThrow(() -> new RuntimeException("Program not found"));

	        Application application = new Application();
	        application.setUser(user);
	        application.setProgram(program);
	        application.setDegreeScore(degreeScore);
	        application.setIncome(income);
	        application.setCity(city);
	        application.setRemote(remote);
	        application.setApplicationDateTime(LocalDateTime.now()); // Set the current date and time
	        
	     // Εκτέλεση των κανόνων Drools για να αξιολογηθεί η αίτηση
	        droolsService.evaluateApplication(application,program);

	     

	        applicationRepo.save(application);

	        return application.getAccepted() ? "application_success" : "application_rejected";
	       
	    }
	    
	 
	   @GetMapping("/applications_list")
	    public String viewApplicationsPage(Model model) {
	        List<Application> listApplications = applicationRepo.findAll();
	        model.addAttribute("listApplications", listApplications);
	        return "applications_list"; //
	    }
	   
	   @GetMapping("/my_applications")
	   public String viewMyApplications(Model model) {
	       // Πάρε το Authentication αντικείμενο
	       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	       String username = authentication.getName(); // Αυτό θα πάρει το όνομα χρήστη

	       // Βρες τον χρήστη με βάση το email
	       User user = repo.findByEmail(username); // Προσάρμοσε αυτή τη μέθοδο στο UserRepository

	       if (user != null) {
	           // Πάρε τις αιτήσεις του χρήστη
	           List<Application> applications = user.getApplications();
	           model.addAttribute("listApplications", applications);
	       } else {
	           model.addAttribute("error", "User not found");
	       }

	       return "my_applications"; // Επιστροφή στην αντίστοιχη σελίδα 
	   }	
	   
	   @GetMapping("/delete_application/{id}")
	    public String deleteApplication(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		// Πάρε το Authentication αντικείμενο
	       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	       String username = authentication.getName(); // Αυτό θα πάρει το όνομα χρήστη

	       // Βρες τον χρήστη με βάση το email
	       User user = repo.findByEmail(username); // Προσάρμοσε αυτή τη μέθοδο στο UserRepository
		   
		   // Βρες την αίτηση με το συγκεκριμένο id		  
	        Application applications = applicationRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Application not found"));

	        // Διαγραφή της αίτησης
	        applicationRepo.delete(applications);

	        // Προσθήκη μηνύματος επιτυχίας στο RedirectAttributes
	        redirectAttributes.addFlashAttribute("message", "Application deleted successfully");
	        
	     // Έλεγχος ρόλου χρήστη
	        if (user.getRole().equals("ROLE_ADMIN")) {
	            return "redirect:/applications_list"; // Ανακατεύθυνση για διαχειριστή
	        } else {
	            return "redirect:/my_applications"; // Ανακατεύθυνση για απλό χρήστη
	        }
	    }
	   
	   @GetMapping("/delete_program/{id}")
	   public String deleteProgram(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
	       Program program = programRepo.findById(id)
	           .orElseThrow(() -> new RuntimeException("Program not found"));

	       programRepo.delete(program);

	       redirectAttributes.addFlashAttribute("message", "Program deleted successfully");
	       return "redirect:/admin?continue";
	   }
	   
	// Εμφάνιση της φόρμας επεξεργασίας
	   @GetMapping("/edit_program/{id}")
	   public String showEditProgramForm(@PathVariable("id") Long id, Model model) {
	       Program program = programRepo.findById(id)
	           .orElseThrow(() -> new RuntimeException("Program not found"));
	       model.addAttribute("program", program);
	       return "edit_program";
	   }

	   // Επεξεργασία του προγράμματος
	   @PostMapping("/update_program")
	   public String updateProgram(@ModelAttribute("program") Program program, RedirectAttributes redirectAttributes) {
	       programRepo.save(program);
	       redirectAttributes.addFlashAttribute("message", "Program updated successfully");
	       return "redirect:/admin?continue";
	   }
	

}


