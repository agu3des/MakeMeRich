package br.edu.ifpb.pweb2.makemerich.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.makemerich.model.Categoria;
import br.edu.ifpb.pweb2.makemerich.model.Conta;
import br.edu.ifpb.pweb2.makemerich.model.Correntista;
import br.edu.ifpb.pweb2.makemerich.model.Movimento;
import br.edu.ifpb.pweb2.makemerich.model.Natureza;
import br.edu.ifpb.pweb2.makemerich.model.Transacao;
import br.edu.ifpb.pweb2.makemerich.repository.TransacaoRepository;

@Component
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;
    
    @Autowired
    private CategoriaService categoriaService;

    public List<Transacao> listarPorConta(Integer contaId) {
        return transacaoRepository.findByContaId(contaId);
    }

    public Page<Transacao> buscarPorConta(Integer contaId, Pageable pageable) {
        return transacaoRepository.findByContaIdOrderByDataDesc(contaId, pageable);
    }

    public Page<Transacao> buscarPorContaEPeriodo(Integer idConta, LocalDate inicio, LocalDate fim, Pageable pageable) {
        return transacaoRepository.findByConta_IdAndDataBetweenOrderByDataDesc(idConta, inicio, fim, pageable);
    }

    public Transacao findByIdTransacao(Integer id) {
        return transacaoRepository.findById(id).orElseThrow();
    }

    public Transacao salvar(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public void deleteById(Integer id) {
        transacaoRepository.deleteById(id);
    }

   
    public Map<Natureza, Map<Categoria, Map<Integer, BigDecimal>>> calcularOrcamentoConsolidado(
        List<Conta> contas, int ano) {
    
    // Obter IDs das contas
    List<Integer> contaIds = contas.stream()
            .map(Conta::getId)
            .collect(Collectors.toList());
    
    // Buscar transações em uma única consulta
    List<Transacao> transacoes = transacaoRepository.findByContaIdInAndYear(contaIds, ano);
    
    // Inicializar estrutura de resultado
    Map<Natureza, Map<Categoria, Map<Integer, BigDecimal>>> resultado = new EnumMap<>(Natureza.class);
    
    // Inicializar todas as naturezas com estruturas vazias
    for (Natureza natureza : Natureza.values()) {
        resultado.put(natureza, new LinkedHashMap<>());  // Usar LinkedHashMap para manter ordem
    }
    
    // Processar cada transação
    for (Transacao transacao : transacoes) {
        Categoria categoria = transacao.getCategoria();
        if (categoria == null) continue;
        
        Natureza natureza = categoria.getNatureza();
        if (natureza == null) continue;
        
        int mes = transacao.getData().getMonthValue();
        BigDecimal valor = transacao.getValor();
        
        // Ajustar sinal conforme movimento
        // CREDITO = positivo (entrada), DEBITO = negativo (saída)
        if (transacao.getMovimento() == Movimento.DEBITO) {
            valor = valor.negate();
        }
        
        // Obter mapa de categorias para a natureza
        Map<Categoria, Map<Integer, BigDecimal>> mapCategoria = resultado.get(natureza);
        
        // Obter mapa de meses para a categoria (ou criar se não existir)
        Map<Integer, BigDecimal> mapMeses = mapCategoria.computeIfAbsent(categoria, k -> {
            Map<Integer, BigDecimal> newMap = new HashMap<>();
            // Inicializar todos os meses com zero
            for (int i = 1; i <= 12; i++) {
                newMap.put(i, BigDecimal.ZERO);
            }
            return newMap;
        });
        
        // Somar o valor ao mês correspondente
        BigDecimal valorAtual = mapMeses.get(mes);
        mapMeses.put(mes, valorAtual.add(valor));
    }
    
    return resultado;
}

    public Map<Natureza, Map<Categoria, BigDecimal>> calcularTotaisPorNatureza(
            Map<Natureza, Map<Categoria, Map<Integer, BigDecimal>>> orcamentoConsolidado) {
        
        Map<Natureza, Map<Categoria, BigDecimal>> totais = new EnumMap<>(Natureza.class);
        
        for (Natureza natureza : Natureza.values()) {
            Map<Categoria, BigDecimal> totaisCategoria = new HashMap<>();
            totais.put(natureza, totaisCategoria);
            
            Map<Categoria, Map<Integer, BigDecimal>> categoriasMap = orcamentoConsolidado.get(natureza);
            if (categoriasMap == null) continue;
            
            for (Map.Entry<Categoria, Map<Integer, BigDecimal>> entry : categoriasMap.entrySet()) {
                Categoria categoria = entry.getKey();
                BigDecimal totalCategoria = entry.getValue().values().stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                totaisCategoria.put(categoria, totalCategoria);
            }
        }
        
        return totais;
    }

    public Map<Natureza, Map<Integer, BigDecimal>> calcularTotaisMensaisPorNatureza(
            Map<Natureza, Map<Categoria, Map<Integer, BigDecimal>>> orcamentoConsolidado) {
        
        Map<Natureza, Map<Integer, BigDecimal>> totais = new EnumMap<>(Natureza.class);
        
        // Inicializar para todas as naturezas
        for (Natureza natureza : Natureza.values()) {
            Map<Integer, BigDecimal> mesesMap = new HashMap<>();
            // Inicializar todos os meses com zero
            for (int mes = 1; mes <= 12; mes++) {
                mesesMap.put(mes, BigDecimal.ZERO);
            }
            totais.put(natureza, mesesMap);
        }
        
        // Calcular totais
        for (Natureza natureza : Natureza.values()) {
            Map<Categoria, Map<Integer, BigDecimal>> categoriasMap = orcamentoConsolidado.get(natureza);
            if (categoriasMap == null) continue;
            
            Map<Integer, BigDecimal> mesesMap = totais.get(natureza);
            
            for (Map<Integer, BigDecimal> categoriaMeses : categoriasMap.values()) {
                for (Map.Entry<Integer, BigDecimal> entry : categoriaMeses.entrySet()) {
                    Integer mes = entry.getKey();
                    BigDecimal valor = entry.getValue();
                    
                    BigDecimal totalAtual = mesesMap.get(mes);
                    mesesMap.put(mes, totalAtual.add(valor));
                }
            }
        }
        
        return totais;
    }

    public BigDecimal calcularSaldoConsolidado(
            Map<Natureza, Map<Categoria, BigDecimal>> totaisPorNatureza) {
        
        BigDecimal saldo = BigDecimal.ZERO;
        
        for (Natureza natureza : Natureza.values()) {
            BigDecimal totalNatureza = totaisPorNatureza.get(natureza).values().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            if (natureza == Natureza.ENTRADA || natureza == Natureza.INVESTIMENTO) {
                saldo = saldo.add(totalNatureza);
            } else if (natureza == Natureza.SAIDA) {
                saldo = saldo.subtract(totalNatureza);
            }
        }
        
        return saldo;
    }

    public void ordenarOrcamentoPorCategoria(
            Map<Natureza, Map<Categoria, Map<Integer, BigDecimal>>> orcamentoConsolidado) {
        
        for (Natureza natureza : Natureza.values()) {
            Map<Categoria, Map<Integer, BigDecimal>> mapCategoria = orcamentoConsolidado.get(natureza);
            if (mapCategoria == null) continue;
            
            Map<Categoria, Map<Integer, BigDecimal>> sortedMap = new LinkedHashMap<>();
            mapCategoria.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getOrdem()))
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
            
            orcamentoConsolidado.put(natureza, sortedMap);
        }
    }
    
    public List<Transacao> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return transacaoRepository.findByDataBetween(inicio, fim);
    }

    public List<Transacao> buscarPorPeriodoEUsuario(LocalDate inicio, LocalDate fim, Integer idUsuario) {
        return transacaoRepository.findByDataBetweenAndConta_Correntista_Id(inicio, fim, idUsuario);
    }

    public List<Transacao> buscarPorContaEPeriodo(Integer idConta, LocalDate inicio, LocalDate fim) {
        return transacaoRepository.findByConta_IdAndDataBetweenOrderByDataDesc(idConta, inicio, fim);
    }

    public List<Object[]> totalPorMesAnoConta(Integer idConta, Integer ano) {
        return transacaoRepository.totalPorMesAnoConta(idConta, ano);
    }

    public List<Object[]> qtdTransacoesPorMesAnoConta(Integer idConta, Integer ano) {
        return transacaoRepository.qtdPorMesAnoConta(idConta, ano);
    }
}