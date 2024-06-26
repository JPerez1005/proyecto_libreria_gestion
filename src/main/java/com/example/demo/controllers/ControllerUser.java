package com.example.demo.controllers;

import com.example.demo.dto.DtoUser;
import com.example.demo.mapper.MapperUser;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.service.ServiceUser;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perez
 */

@RestController
@RequestMapping("/user")
public class ControllerUser {

    //datos usuario
    @Autowired
    private ServiceUser su;
    
    @Autowired
    private RepositoryUser ru;
    
    @Autowired
    private MapperUser mu;
    
    @GetMapping("/listar_usuarios")
    public ResponseEntity<List<DtoUser>> listarUsuarios(){
        List<DtoUser> usuarios=(List<DtoUser>) su.listaUsuarios();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DtoUser> listarUsuarioPorId(@PathVariable Long id){
        Optional<DtoUser> DtoUserOptional=su.getUserById(id);
        return DtoUserOptional.map(user->new ResponseEntity<>(user,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
//    @PostMapping("/agregar_usuario")
//    public ResponseEntity<DtoUser> guardarUsuario
//        (@RequestBody DtoUser dtoU) throws ParseException{
//        User newUser=su.createUser(dtoU);
//        
//        if(newUser==null){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(dtoU,HttpStatus.CREATED);
//    }
        
    @PostMapping("/agregar_usuario")
    public ResponseEntity<DtoUser> guardarUsuario(@RequestBody DtoUser du) throws ParseException{
        DtoUser createdLibro=su.createUser(du);
        return new ResponseEntity<>(createdLibro,HttpStatus.OK);
    }
    
    @PutMapping("/modificar_usuario/{id}")
    public ResponseEntity<DtoUser> modificarUsuario
        (@PathVariable Long id,@RequestBody DtoUser du) throws ParseException{
        
            DtoUser updateUsuario=su.updateUser(id, du);
            
            if (updateUsuario!=null) {
                return new ResponseEntity<>(updateUsuario,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
        
    @PostMapping("/ingresar")
    public ResponseEntity<String> ingreso(@RequestBody(required=true) Map<String,String> requestMap){
        try{
            return su.ingresar(requestMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return new ResponseEntity<>("algo salió mal logueandose",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
