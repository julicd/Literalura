package br.com.alura.literalura.service;

import java.util.List;

public interface IConverteDados {
    <T> List<T> obterLista(String json, Class<T> classe);
}
