package br.org.crvnluz.editora.clubelivro.controlador.seguranca;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.org.crvnluz.editora.clubelivro.entidade.seguranca.Usuario;
import br.org.crvnluz.editora.clubelivro.servico.seguranca.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/usuario/editar")
	public ModelAndView editar(Principal principal) {
		ModelAndView model = new ModelAndView("admin/usuario");
		model.addObject("usuario", service.getUsuario(principal.getName()));
		return model;
	}
	
	@GetMapping(value = {"/login"})
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@PostMapping("/usuario/salvar")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ModelAndView salvar(@RequestParam("id") Long id, @RequestParam("nome") String nome, @RequestParam("email") String email, 
				@RequestParam("senha") String senha, @RequestParam("confirmacao") String confirmacaoSenha) throws Throwable {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setEmail(email);
		service.salvar(usuario, senha, confirmacaoSenha);
		return new ModelAndView("index");
	}
}
