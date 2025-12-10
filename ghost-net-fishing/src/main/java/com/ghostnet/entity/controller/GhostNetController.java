package com.ghostnet.controller;

import com.ghostnet.entity.GhostNet;
import com.ghostnet.entity.NetStatus;
import com.ghostnet.entity.Person;
import com.ghostnet.service.GhostNetService;
import com.ghostnet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/nets")
public class GhostNetController {

    @Autowired
    private GhostNetService ghostNetService;

    @Autowired
    private PersonService personService;

    // MUST 1: Report ghost nets (anonymously)
    @GetMapping("/report")
    public String showReportForm(Model model) {
        model.addAttribute("ghostNet", new GhostNet());
        return "report-net";
    }

    @PostMapping("/report")
    public String reportGhostNet(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double estimatedSize,
            @RequestParam(required = false) String reporterName,
            @RequestParam(required = false) String reporterPhone,
            @RequestParam(defaultValue = "false") boolean anonymous,
            RedirectAttributes redirectAttributes) {
        
        Person reporter;
        if (anonymous) {
            reporter = personService.createAnonymousPerson();
        } else {
            reporter = personService.createPerson(reporterName, reporterPhone, false);
        }
        
        ghostNetService.reportGhostNet(latitude, longitude, estimatedSize, reporter);
        redirectAttributes.addFlashAttribute("message", "Ghost net reported successfully!");
        return "redirect:/nets/view";
    }

    // MUST 3: View nets to be recovered
    @GetMapping("/view")
    public String viewNetsToRecover(Model model) {
        List<GhostNet> nets = ghostNetService.getNetsToBeRecovered();
        model.addAttribute("nets", nets);
        return "view-nets";
    }

    @GetMapping("/all")
    public String viewAllNets(Model model) {
        List<GhostNet> nets = ghostNetService.getAllGhostNets();
        model.addAttribute("nets", nets);
        return "all-nets";
    }

    // MUST 2: Register for recovery
    @GetMapping("/register/{id}")
    public String showRegisterForm(@PathVariable Long id, Model model) {
        GhostNet net = ghostNetService.getGhostNetById(id).orElse(null);
        if (net == null) {
            return "redirect:/nets/view";
        }
        model.addAttribute("net", net);
        return "register-recovery";
    }

    @PostMapping("/register/{id}")
    public String registerForRecovery(
            @PathVariable Long id,
            @RequestParam String salvagerName,
            @RequestParam String salvagerPhone,
            RedirectAttributes redirectAttributes) {
        
        Person salvager = personService.createPerson(salvagerName, salvagerPhone, false);
        GhostNet net = ghostNetService.registerForRecovery(id, salvager);
        
        if (net != null) {
            redirectAttributes.addFlashAttribute("message", 
                "Successfully registered for recovery of ghost net #" + id);
        } else {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to register. Net may already be assigned.");
        }
        
        return "redirect:/nets/view";
    }

    // MUST 4: Mark as recovered
    @GetMapping("/my-recoveries")
    public String showMyRecoveries(Model model) {
        List<GhostNet> nets = ghostNetService.getGhostNetsByStatus(NetStatus.RECOVERY_PENDING);
        model.addAttribute("nets", nets);
        return "my-recoveries";
    }

    @PostMapping("/mark-recovered/{id}")
    public String markAsRecovered(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        GhostNet net = ghostNetService.markAsRecovered(id);
        
        if (net != null) {
            redirectAttributes.addFlashAttribute("message", 
                "Ghost net #" + id + " marked as recovered!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to mark as recovered.");
        }
        
        return "redirect:/nets/my-recoveries";
    }

    // COULD 7: Mark as lost
    @PostMapping("/mark-lost/{id}")
    public String markAsLost(
            @PathVariable Long id,
            @RequestParam String personName,
            @RequestParam String personPhone,
            RedirectAttributes redirectAttributes) {
        
        Person person = personService.createPerson(personName, personPhone, false);
        GhostNet net = ghostNetService.markAsLost(id, person);
        
        if (net != null) {
            redirectAttributes.addFlashAttribute("message", 
                "Ghost net #" + id + " marked as lost.");
        } else {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to mark as lost. Cannot be done anonymously.");
        }
        
        return "redirect:/nets/view";
    }

    // COULD 6: View salvager assignments
    @GetMapping("/assignments")
    public String viewAssignments(Model model) {
        List<GhostNet> nets = ghostNetService.getGhostNetsByStatus(NetStatus.RECOVERY_PENDING);
        model.addAttribute("nets", nets);
        return "assignments";
    }
}