package com.example.linkif.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.linkif.model.EmpresaModel;
import com.example.linkif.model.UserModel;
import com.example.linkif.model.VagaModel;
import com.example.linkif.repository.EmpresaRepository;
import com.example.linkif.repository.UserRepository;
import com.example.linkif.repository.VagaRepository;

@Controller
public class ControllerLinkif {
    @Autowired
    VagaRepository repositoryVaga;

    @Autowired
    UserRepository repositoryUser;

    @Autowired
    EmpresaRepository repositoryEmpresa;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView Login() {
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @RequestMapping(value = "/empresa/candidatos/{id}", method = RequestMethod.GET)
    public ModelAndView EmpresaCandidatos(@PathVariable("id") int id,
            RedirectAttributes attributes) {
        System.out.println("EMPREASA CANDIDATA ");
        ModelAndView mv = new ModelAndView("candidatosEmpresa");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String empresaName = auth.getName().toString();
        Optional<VagaModel> vaga = repositoryVaga.findById(id);
        List<UserModel> vagaUsers = new ArrayList<UserModel>();
        List<String> users = vaga.get().getUsersNames();
        for (String user : users) {
            Optional<UserModel> u = repositoryUser.findByUsername(user);
            vagaUsers.add(u.get());
        }

        mv.addObject("users", vagaUsers);
        Optional<EmpresaModel> empresa = repositoryEmpresa.findByUsername(empresaName);

        mv.addObject("username", empresaName);
        mv.addObject("imagem", empresa.get().getImagem());
        return mv;
    }

    @RequestMapping(value = "/vagas/candidata/{id}", method = RequestMethod.GET)
    public String Candidata(@PathVariable("id") int id,
            RedirectAttributes attributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName().toString();
        repositoryVaga.insertIntoVagaUsersNames(id, email);

        return "redirect:/vagas";
    }

    @RequestMapping(value = "/vagas/minhasvagas/{id}", method = RequestMethod.GET)
    public ModelAndView VagasCandidatadas(@PathVariable("id") int id,
            RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("userVagas");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName().toString();
        Optional<UserModel> user = repositoryUser.findByUsername(username);
        mv.addObject("imagem", user.get().getImagem());
        mv.addObject("userId", user.get().getUserId());

        List<VagaModel> vagas = repositoryVaga.findByusersNamesLike(username);
        mv.addObject("vagas", vagas);

        return mv;
    }

    @RequestMapping(value = "/vagas/pesquisar", method = RequestMethod.POST)
    public ModelAndView getTipoAndEmpresa(
            @RequestParam("tipo") int tipo,
            @RequestParam("nomeEmpresa") String emp) throws IOException {

        ModelAndView mv = new ModelAndView("vagas");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String empresaName = auth.getName().toString();

        Optional<EmpresaModel> empresa = repositoryEmpresa.findByUsername(empresaName);
        Optional<UserModel> user = repositoryUser.findByUsername(empresaName);
        boolean empresaauth = false;
        boolean userauth = false;
        boolean nonauth = false;

        if (empresaName.equals("anonymousUser")) {
            nonauth = true;
            mv.addObject("nonauth", nonauth);

        } else {
            if (!empresa.isEmpty()) {
                empresaauth = true;
                mv.addObject("empresaauth", empresaauth);
                mv.addObject("imagem", empresa.get().getImagem());
            } else {
                userauth = true;
                mv.addObject("userauth", userauth);
                mv.addObject("userId", user.get().getUserId());
                mv.addObject("imagem", user.get().getImagem());
            }
        }

        mv.addObject("username", empresaName);

        if (tipo > 0) {
            List<VagaModel> vaga = repositoryVaga.findByTipoLike(tipo);
            mv.addObject("vagas", vaga);
        }
        if (emp != "") {
            String name = emp;
            List<VagaModel> empresaname = repositoryVaga.findByempresaNameLike(name);
            mv.addObject("vagas", empresaname);
        }

        return mv;
    }

    @RequestMapping(value = "/empresa/delete/{id}", method = RequestMethod.GET)
    public String deletePostagens(@PathVariable("id") int id, RedirectAttributes attributes) {
        repositoryVaga.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Deletado com sucesso");
        return "redirect:/vagas";
    }

    @RequestMapping(value = "/empresa/{username}", method = RequestMethod.GET)
    public ModelAndView EmpresaVagas(@PathVariable("username") String empresaName) {
        ModelAndView mv = new ModelAndView("empresaVagas");
        Optional<EmpresaModel> empresa = repositoryEmpresa.findByUsername(empresaName);

        List<VagaModel> vagas = repositoryVaga.findAll();
        List<VagaModel> vagasdaEmpresa = new ArrayList<>();
        for (VagaModel vaga : vagas) {
            if (vaga.getEmpresaName().equals(empresa.get().getNome())) {
                vagasdaEmpresa.add(vaga);
            }
        }

        mv.addObject("imagem", empresa.get().getImagem());
        mv.addObject("vagas", vagasdaEmpresa);

        return mv;
    }

    @RequestMapping(value = "/empresa/{id}", method = RequestMethod.POST)
    public String EmpresaAtivaVaga(@PathVariable("id") int id,
            RedirectAttributes attributes) {
        Optional<VagaModel> vaga = repositoryVaga.findById(id);

        repositoryVaga.upadteTbVagasStatus(id, !vaga.get().isStatus());
        return "redirect:/vagas";
    }

    @RequestMapping(value = "/vagas", method = RequestMethod.GET)
    public ModelAndView getVagas() {
        ModelAndView mv = new ModelAndView("vagas");
        List<VagaModel> vagas = repositoryVaga.findAll();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String empresaName = auth.getName().toString();

        Optional<EmpresaModel> empresa = repositoryEmpresa.findByUsername(empresaName);
        Optional<UserModel> user = repositoryUser.findByUsername(empresaName);

        boolean empresaauth = false;
        boolean userauth = false;
        boolean nonauth = false;

        if (empresaName.equals("anonymousUser")) {
            nonauth = true;
            mv.addObject("nonauth", nonauth);

        } else {
            if (!empresa.isEmpty()) {
                empresaauth = true;
                mv.addObject("empresaauth", empresaauth);
                mv.addObject("imagem", empresa.get().getImagem());
            } else {
                userauth = true;
                mv.addObject("userauth", userauth);
                mv.addObject("imagem", user.get().getImagem());
                mv.addObject("userId", user.get().getUserId());
            }
        }

        mv.addObject("username", empresaName);

        mv.addObject("vagas", vagas);
        return mv;
    }

    @RequestMapping(value = "/vagas/{id}", method = RequestMethod.GET)
    public ModelAndView getVagaDetails(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("vagas");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String empresaName = auth.getName().toString();
        Optional<EmpresaModel> empresa = repositoryEmpresa.findByUsername(empresaName);
        Optional<UserModel> user = repositoryUser.findByUsername(empresaName);

        boolean empresaauth = false;
        boolean userauth = false;
        boolean nonauth = false;

        if (empresaName.equals("anonymousUser")) {
            nonauth = true;
            mv.addObject("nonauth", nonauth);

        } else {
            if (!empresa.isEmpty()) {
                empresaauth = true;
                mv.addObject("empresaauth", empresaauth);
                mv.addObject("imagem", empresa.get().getImagem());

            } else {
                userauth = true;
                mv.addObject("userauth", userauth);
                mv.addObject("imagem", user.get().getImagem());
                mv.addObject("userId", user.get().getUserId());

            }
        }

        List<VagaModel> vagas = repositoryVaga.findAll();
        Optional<VagaModel> vaga = repositoryVaga.findById(id);
        mv.addObject("vagaid", vaga.get().getId());
        mv.addObject("titulo", vaga.get().getTitulo());
        mv.addObject("empresa", vaga.get().getEmpresaName());
        mv.addObject("regiao", vaga.get().getRegiao());
        mv.addObject("salario", vaga.get().getSalario());
        mv.addObject("status", vaga.get().isStatus());
        mv.addObject("descricao", vaga.get().getDescricao());
        mv.addObject("tipo", vaga.get().getTipo());
        mv.addObject("username", empresaName);

        mv.addObject("vagas", vagas);
        return mv;
    }

    @RequestMapping(value = "/cadastro/vagas", method = RequestMethod.GET)
    public ModelAndView cadastraVaga() {
        ModelAndView mv = new ModelAndView("cadastroVaga");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String empresaName = auth.getName().toString();
        boolean authenticated;
        if (empresaName.equals("anonymousUser")) {
            authenticated = false;
        } else {
            authenticated = true;
        }
        Optional<EmpresaModel> empresa = repositoryEmpresa.findByUsername(empresaName);

        mv.addObject("imagem", empresa.get().getImagem());
        mv.addObject("username", empresaName);
        mv.addObject("authenticated", authenticated);

        return mv;
    }

    @RequestMapping(value = "/cadastro/vagas", method = RequestMethod.POST)
    public String saveVaga(@Valid VagaModel vaga, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastro/vagas";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String empresaName = auth.getName().toString();
        vaga.setStatus(true);
        Optional<EmpresaModel> empresa = repositoryEmpresa.findByUsername(empresaName);
        vaga.setEmpresaName(empresa.get().getNome());
        vaga.setEmpresaImagem(empresa.get().getImagem());

        repositoryVaga.save(vaga);
        return "redirect:/vagas";
    }

    @RequestMapping(value = "/cadastro/user", method = RequestMethod.GET)
    public ModelAndView Cadastro() {
        ModelAndView mv = new ModelAndView("cadastroUser");
        return mv;
    }

    @RequestMapping(value = "/cadastro/user", method = RequestMethod.POST)
    public String saveEmpresa(@Valid UserModel user, BindingResult result, RedirectAttributes attributes,
            @RequestParam("file") MultipartFile imagem) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastro/user";

        }
        List<UserModel> users = repositoryUser.findAll();
        for (UserModel userModel : users) {
            if (user.getUsername().equals(userModel.getUsername())) {
                attributes.addFlashAttribute("mensagem", "Email já cadastrado!");
                return "redirect:/cadastro/user";
            }
        }
        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                String nomeImagem = LocalDate.now() + imagem.getOriginalFilename();
                Path caminho = Paths.get("./src/main/resources/static/img/" + nomeImagem);
                Files.write(caminho, bytes);
                user.setImagem(nomeImagem);
            }
        } catch (IOException e) {
            System.out.println("ERRO NA IMAGEM" + e);
        }

        user.setUserId(1 + (int) (Math.random() * 4123));
        int cnpjoucpf = (user.getCnpjoucpf());

        String ecode = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(ecode);

        user.setRole_id(11);
        user.setCnpjoucpf(cnpjoucpf);
        repositoryUser.save(user);
        try {
            repositoryUser.insertIntoTbUsers(user.getUserId(), user.getRole_id());

        } catch (Exception e) {
            System.out.println("ERRO USER" + e);
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/cadastro/empresa", method = RequestMethod.GET)
    public ModelAndView CadastroEmpresa() {
        ModelAndView mv = new ModelAndView("cadastroEmpresa");
        return mv;
    }

    @RequestMapping(value = "/cadastro/empresa", method = RequestMethod.POST)
    public String saveEmpresa(@Valid EmpresaModel empresa, BindingResult result, RedirectAttributes attributes,
            @RequestParam("file") MultipartFile imagem) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            System.out.println(result.toString());
            return "redirect:/cadastro/empresa";

        }
        List<EmpresaModel> empresas = repositoryEmpresa.findAll();
        for (EmpresaModel EmpresaModel : empresas) {
            if (empresa.getUsername().equals(EmpresaModel.getUsername())) {
                attributes.addFlashAttribute("mensagem", "Email já cadastrado!");
                return "redirect:/cadastro/empresa";
            }
        }
        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                String nomeImagem = LocalDate.now() + imagem.getOriginalFilename();
                Path caminho = Paths.get("./src/main/resources/static/img/" + nomeImagem);
                Files.write(caminho, bytes);
                empresa.setImagem(nomeImagem);
            }
        } catch (IOException e) {
            System.out.println("ERRO NA IMAGEM" + e);
        }

        empresa.setEmpresaId(1 + (int) (Math.random() * 4123));
        int cnpjoucpf = (empresa.getCnpjoucpf());

        String ecode = new BCryptPasswordEncoder().encode(empresa.getPassword());
        empresa.setPassword(ecode);

        empresa.setRole_id(12);
        empresa.setCnpjoucpf(cnpjoucpf);

        repositoryEmpresa.save(empresa);

        repositoryEmpresa.insertIntoTbUsers(empresa.getEmpresaId(), empresa.getRole_id());

        return "redirect:/login";
    }

    @RequestMapping(value = "/img/{imagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File("./src/main/resources/static/img/" + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;

    }

}
