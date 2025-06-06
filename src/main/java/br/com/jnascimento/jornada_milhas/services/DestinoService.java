package br.com.jnascimento.jornada_milhas.services;

import br.com.jnascimento.jornada_milhas.models.DepoimentoModel;
import br.com.jnascimento.jornada_milhas.models.DestinoModel;
import br.com.jnascimento.jornada_milhas.models.dtos.DestinoDto;
import br.com.jnascimento.jornada_milhas.repositories.DepoimentoRepository;
import br.com.jnascimento.jornada_milhas.repositories.DestinoRepository;
import br.com.jnascimento.jornada_milhas.services.exceptions.DepoimentoException;
import br.com.jnascimento.jornada_milhas.services.exceptions.DestinoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DestinoService {

    @Autowired
    private DestinoRepository repository;

    public DestinoDto getOne(Long id){
        var destino = repository.findById(id).orElseThrow(DestinoException::new);
         return new DestinoDto(destino);
    }
    public List<DestinoModel> getAll(){
        var list = repository.findAll();
        if(list.isEmpty()){
            throw new DestinoException("Ainda não existe nenhum destino");
        }
        return list;
    }
    public DestinoModel saveOrUpdate(DestinoModel model){
        if (model.getId() == null){
            return repository.save(model);
        }else {
            return  repository.save(updateData(model));
        }
    }

    private DestinoModel updateData(DestinoModel model) {
        var data = repository.findById(model.getId()).orElseThrow(DestinoException::new);
            data.setNome((model.getNome()!=null)?model.getNome():data.getNome());
            data.setFoto1((model.getFoto1()!=null)?model.getFoto1():data.getFoto1());
            data.setFoto2((model.getFoto2()!=null)?model.getFoto2():data.getFoto2());
            data.setMeta((model.getMeta()!=null)?model.getMeta():data.getMeta());
            data.setTextoDescritivo((model.getTextoDescritivo()!=null)?model.getTextoDescritivo():data.getTextoDescritivo());

            return data;
    }

    public void delete(Long id){
        var depoimento = repository.findById(id);
        if (depoimento.isEmpty()){
            throw new DestinoException("Id: "+ id +" não encontrado!");
        }
         repository.delete(depoimento.get());
    }
    public DestinoModel findByName(String nome){
        var destino = repository.findByNomeContainingIgnoreCase(nome);
        if (destino.isEmpty()){
            throw new DestinoException("Nenhum destino foi encontrado");
        }
        return destino.get();
    }
}
