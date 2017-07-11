package edu.sibinfo.spring.web.module05.controller;

import edu.sibinfo.spring.web.module05.dto.ClientDTO;
import edu.sibinfo.spring.web.module05.dto.ClientRegistrationDTO;
import edu.sibinfo.spring.web.module05.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("client")
public class ClientController {
  private final ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    super();
    this.clientService = clientService;
  }

  @RequestMapping("/")
  public String all(Model model) {
    return all(0, model);
  }

  @RequestMapping("all")
  public String all(@RequestParam(name = "page", required = false, defaultValue = "0") int page, Model model) {
    Page<ClientDTO> clientsPage = clientService.findAll(page);
    model.addAttribute("clients", clientsPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("maxPage", clientsPage.getTotalPages());
    return "client/all";
  }

  @GetMapping("register")
  @PreAuthorize("hasRole('USER')")
  public String register(ClientRegistrationDTO clientRegistrationDTO) {
    return "client/register";
  }

  @PostMapping("register")
  public String register(@ModelAttribute @Valid ClientRegistrationDTO dto, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "client/register";
    }
    clientService.register(dto.getFirstName(), dto.getFamilyName(), dto.getRegistrationPhone());
    return "redirect:all";
  }

  @GetMapping("edit")
  @Secured({"ROLE_ADMIN"})
  public String edit(@RequestParam(name = "id") long clientId, Model model) {
    ClientDTO clientDto = clientService.findOne(clientId);
    model.addAttribute("client", clientDto);
    return "client/edit";
  }

  @PostMapping("edit")
  public String edit(@ModelAttribute ClientDTO dto) {
    clientService.update(dto);
    return "redirect:all";
  }
}
