package com.example.linkif.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.linkif.repository.VagaRepository;
import com.example.linkif.model.UserModel;
import com.example.linkif.repository.UserRepository;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

@Controller
public class ControllerLinkif {
    @Autowired
    VagaRepository repositoryVaga;

    @Autowired
    UserRepository repositoryUser;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView Login() {
        ModelAndView mv = new ModelAndView("home");

        // List<Categorias> categorias = repositoryCar.findAll();
        // mv.addObject("categorias", categorias);

        return mv;
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public ModelAndView Cadastro() {
        ModelAndView mv = new ModelAndView("cadastro");
        // List<Categorias> categorias = repositoryCar.findAll();
        // mv.addObject("categorias", categorias);

        return mv;
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String saveUser(@Valid UserModel user, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            System.out.println(result);
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastro";

        }
        List<UserModel> users = repositoryUser.findAll();
        for (UserModel userModel : users) {
            if (user.getUsername().equals(userModel.getUsername())) {
                attributes.addFlashAttribute("mensagem", "Verifique os campos!");
                return "redirect:/cadastro";
            }
        }
        user.setUserId(1 + (int) (Math.random() * 4123));
        int cnpjoucpf = (user.getCnpjoucpf());

        String ecode = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(ecode);

        if (user.getIdade() == null) {
            user.setRole_id(12);
        } else {
            user.setRole_id(11);
        }

        user.setCnpjoucpf(cnpjoucpf);
        repositoryUser.save(user);

        repositoryUser.insertIntoTbUsers(user.getUserId(), user.getRole_id());

        System.out.println(user.getUserId());

        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        ModelAndView mv = new ModelAndView("index");

        return mv;
    }
    // // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    // @RequestMapping(value = "/index", method = RequestMethod.GET)
    // public ModelAndView getGetCategoria() {
    // ModelAndView mv = new ModelAndView("index");

    // List<Categorias> categorias = repositoryCar.findAll();
    // mv.addObject("categorias", categorias);

    // List<Veiculos> veiculos = repositoryVe.findAll();
    // mv.addObject("veiculos", veiculos);

    // return mv;
    // }

    // // @PreAuthorize("hasRole('ROLE_USER')")
    // @RequestMapping(value = "/veiculos/{id}", method = RequestMethod.GET)
    // public ModelAndView getVeiculo(@PathVariable("id") int id) {
    // ModelAndView mv = new ModelAndView("veiculo");
    // List<Categorias> categorias = repositoryCar.findAll();
    // Optional<Veiculos> veiculo = repositoryVe.findById(id);
    // mv.addObject("id", veiculo.get().getId());
    // mv.addObject("placa", veiculo.get().getPlaca());
    // mv.addObject("cor", veiculo.get().getCor());
    // mv.addObject("modelo", veiculo.get().getModelo());
    // mv.addObject("marca", veiculo.get().getMarca());
    // mv.addObject("preco", veiculo.get().getPreco());
    // mv.addObject("ano", veiculo.get().getAno());
    // mv.addObject("imagem", veiculo.get().getImagem());
    // mv.addObject("categoria", veiculo.get().getcategoria());

    // mv.addObject("categorias", categorias);
    // return mv;
    // }

    // // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    // @RequestMapping(value = "/save", method = RequestMethod.GET)
    // public ModelAndView save() {
    // ModelAndView mv = new ModelAndView("formulario");

    // List<Categorias> categorias = repositoryCar.findAll();
    // mv.addObject("categorias", categorias);

    // return mv;
    // }

    // // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    // @RequestMapping(value = "/save", method = RequestMethod.POST)
    // public String savePost(@Valid Veiculos veiculo, BindingResult result,
    // RedirectAttributes attributes,
    // @RequestParam("file") MultipartFile imagem) {
    // if (result.hasErrors()) {
    // System.out.println(result);
    // attributes.addFlashAttribute("mensagem", "Verifique os campos!");
    // return "redirect:/save";

    // }
    // try {
    // if (!imagem.isEmpty()) {
    // byte[] bytes = imagem.getBytes();
    // String nomeImagem = LocalDate.now() + imagem.getOriginalFilename();
    // Path caminho = Paths.get("./src/main/resources/static/img/" + nomeImagem);
    // Files.write(caminho, bytes);
    // veiculo.setImagem(nomeImagem);
    // }
    // } catch (IOException e) {
    // System.out.println("ERRO NA IMAGEM" + e);
    // }
    // System.out.println("////////////////////////" + veiculo.getcategoria());
    // repositoryVe.save(veiculo);
    // return "redirect:/index";
    // }

    // @RequestMapping(value = "/img/{imagem}", method = RequestMethod.GET)
    // @ResponseBody
    // public byte[] getImagem(@PathVariable("imagem") String imagem) throws
    // IOException {
    // File imagemArquivo = new File("./src/main/resources/static/img/" + imagem);
    // if (imagem != null || imagem.trim().length() > 0) {
    // return Files.readAllBytes(imagemArquivo.toPath());
    // }
    // return null;

    // }

    // // @RequestMapping(value = "/Veiculos/delete/{id}", method =
    // RequestMethod.GET)
    // // public String deleteVeiculos(@PathVariable("id")int id, RedirectAttributes
    // // attributes) {
    // // RepositoryIfcar.deleteById(id);
    // // attributes.addFlashAttribute("mensagem", "Deletado com sucesso");
    // // return "redirect:/Veiculos";
    // // }
    // @RequestMapping(value = "/modelos", method = RequestMethod.POST)
    // public ModelAndView getVeiculosByModeloLike(
    // @RequestParam("pesquisar") String buscar,
    // @RequestParam("ano") String ano) throws IOException {

    // ModelAndView mv = new ModelAndView("index");
    // if (buscar != "") {
    // List<Veiculos> modelo = repositoryVe.findVeiculosByModeloLike(buscar);
    // System.out.println("modelo" + buscar);
    // mv.addObject("veiculos", modelo);
    // }
    // if (ano != "") {
    // int realano = Integer.parseInt(ano);
    // List<Veiculos> vano = repositoryVe.findVeiculosByAnoLike(realano);
    // System.out.println("ano" + realano);
    // mv.addObject("veiculos", vano);
    // }

    // List<Categorias> categorias = repositoryCar.findAll();
    // mv.addObject("categorias", categorias);

    // return mv;
    // }

    // @RequestMapping(value = "/index/categorias/{categoria}", method =
    // RequestMethod.GET)
    // public ModelAndView getVeiculoByCategoria(@PathVariable("categoria") int
    // categoria) {
    // ModelAndView mv = new ModelAndView("index");
    // List<Veiculos> veiculo = repositoryVe.findVeiculosBycategoriaLike(categoria);
    // mv.addObject("veiculos", veiculo);

    // List<Categorias> categorias = repositoryCar.findAll();
    // mv.addObject("categorias", categorias);
    // return mv;
    // }

}
