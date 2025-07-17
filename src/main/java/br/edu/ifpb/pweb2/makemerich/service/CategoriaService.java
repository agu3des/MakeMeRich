package br.edu.ifpb.pweb2.makemerich.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.makemerich.model.Categoria;
import br.edu.ifpb.pweb2.makemerich.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Retorna todas as categorias ativas (pode filtrar ativa=true, se quiser)
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Buscar por id
    public Categoria buscarPorId(Long id) {
        Optional<Categoria> opt = categoriaRepository.findById(id);
        return opt.orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));
    }

    public List<Categoria> buscarAtivasOrdenadas() {
        return categoriaRepository.findByAtivaTrueOrderByNaturezaAscOrdemAsc();
    }

    // Salvar nova categoria ou atualizar existente
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Desativar categoria
    public void desativar(Long id) {
        Categoria cat = categoriaRepository.findById(id).orElse(null);
        if (cat != null) {
            cat.setAtiva(false);
            categoriaRepository.save(cat);
        }
    }
}
