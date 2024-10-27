package com.flex360.api_flex360.infra.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.flex360.api_flex360.models.Cadeira;
import com.flex360.api_flex360.models.Categoria;
import com.flex360.api_flex360.models.Cor;
import com.flex360.api_flex360.repository.CadeiraRepository;
import com.flex360.api_flex360.services.CadeiraService;

@Component
public class CadeiraDataInitializer implements CommandLineRunner {

    CadeiraService cadeiraService;
    CadeiraRepository cadeiraRepository;

    @Override
    public void run(String... args) throws Exception {

        if (cadeiraRepository.findAll().isEmpty()) {

            Cadeira novaCadeira = new Cadeira();

            novaCadeira.setNome("Cadeira Tecton");
            novaCadeira.setDescricao(
                    "Cadeira Tecton Golden Unique / Flexform Encosto e assento revestido em pvc sintético. Apoio de braço, estrutura do encosto e base na cor Carbon Grey (Grafite).");
            novaCadeira.setInformacoes(
                    "Desenvolvida pelos renomados designers Baldanzi & Novelli, a Tecton possui um design marcado pelas linhas retas, além de proporcionar ao usuário conforto e liberdade de movimento");
            novaCadeira.setTemp_garantia(5);
            novaCadeira.setPreco(1899.00f);
            novaCadeira.setDimensoes("120cm x 70cm x 50cm");
            novaCadeira.setFoto_dimensoes(
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/tecton/ddp-tecton.jpeg");
            novaCadeira.setFoto_banner(
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/tecton/banner-tecton.jpeg");
            novaCadeira.setDesc_encosto(
                    "Encosto com estrutura injetada em resina plastica, com apoio lombar regulável na altura..");
            novaCadeira.setDesc_apoio("Apoia braço 360, regulável na altura, profundidade, abertura e ângulo.");
            novaCadeira
                    .setDesc_rodinha("Rodizios / Rodinha com 65 mm de diâmetro indicado para qualquer tipo de piso.");
            novaCadeira.setDesc_ajuste_altura(
                    "Mecanismo ajuste de altura do assento. Inclinação do encosto com 4 pontos de parada, possui movemento relax.");
            novaCadeira.setDesc_revestimento(
                    "Revestimento em pvc sintético de alta qualidade. Facilidade na assepsia e fogo retardante.");

            List<Categoria> categorias = new ArrayList<>();
            categorias.add(new Categoria(UUID.randomUUID(), "Clássica"));
            novaCadeira.setCategorias(categorias);

            List<Cor> cores = new ArrayList<>();

            cores.add(new Cor(UUID.randomUUID(), "Preto", "#171426",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/tecton/cadeira-tecton-all-black-unique.jpeg"));
            cores.add(new Cor(UUID.randomUUID(), "Dourado", "#B0712A",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/tecton/cadeira-tecton-golden-unique.jpeg"));
            cores.add(new Cor(UUID.randomUUID(), "Marrom", "#403136",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/tecton/cadeira-tecton-coffee-unique.jpeg"));
            cores.add(new Cor(UUID.randomUUID(), "Azul", "#2A2D40",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/tecton/cadeira-tecton-night-blue-unique.jpeg"));

            novaCadeira.setCores(cores);

            Cadeira cadeiraBigOne = new Cadeira();

            cadeiraBigOne.setNome("Cadeira Big One");
            cadeiraBigOne.setDescricao(
                    "Base 5 astes fabricada em aço com capa de polipropileno, coluna giratória com regulagem protegidas por capa telescópica, braços reguláveis na altura e largura, apoio em poliuretano injetado macio para maior conforto na utilização.");
            cadeiraBigOne.setInformacoes(
                    "A Golden BIG ONE Poltrona Luxo de altíssima qualidade, projetada para suportar até 150 kg, além de super-resistente, mantém o conforto, elegância e Ergonomia em seu ambiente de trabalho.");
            cadeiraBigOne.setTemp_garantia(1);
            cadeiraBigOne.setPreco(1600.00f);
            cadeiraBigOne.setDimensoes("120cm x 70cm x 50cm");
            cadeiraBigOne.setFoto_dimensoes(
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/big-one/ddp-big-one.jpeg");
            cadeiraBigOne.setFoto_banner(
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/big-one/banner-big-one.jpeg");
            cadeiraBigOne
                    .setDesc_encosto("Encosto com estrutura injetada em resina plastica, com regulagem de altura.");
            cadeiraBigOne.setDesc_apoio("Apoia braços 2D, regulável na altura e largura.");
            cadeiraBigOne.setDesc_rodinha("Rodizio especial com freio, suporta alta capacidade de peso");
            cadeiraBigOne.setDesc_ajuste_altura(
                    "Mecanismo ajuste de altura do assento. Inclinação do encosto com 4 pontos de parada, possui movimento relax.");
            cadeiraBigOne.setDesc_revestimento("Revestimento em pvc sintético.");

            List<Categoria> categoriasBigOne = new ArrayList<>();
            categoriasBigOne.add(new Categoria(UUID.randomUUID(), "Contemporânea"));
            categoriasBigOne.add(new Categoria(UUID.randomUUID(), "Minimalista"));
            categoriasBigOne.add(new Categoria(UUID.randomUUID(), "Clássica"));
            cadeiraBigOne.setCategorias(categoriasBigOne);

            List<Cor> coresBigOne = new ArrayList<>();
            coresBigOne.add(new Cor(UUID.randomUUID(), "Preto", "#171426",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/big-one/cadeira-golden-big-one-150kg.jpeg"));
            cadeiraBigOne.setCores(coresBigOne);

            Cadeira cadeiraUni = new Cadeira();

            cadeiraUni.setNome("Cadeira Uni");
            cadeiraUni.setDescricao(
                    "Cadeira Uni / Flexform Tela em nylon, estrutura do encosto com apoio lombar, base preto e apoio de braço preto.");
            cadeiraUni.setInformacoes(
                    "Uni, perfeita para mesas de escritório ou home office. Além de estilo, a Uni garante a postura correta que beneficia a circulação sanguínea do corpo.");
            cadeiraUni.setTemp_garantia(5);
            cadeiraUni.setPreco(899.00f);
            cadeiraUni.setDimensoes("120cm x 70cm x 50cm");
            cadeiraUni.setFoto_dimensoes("https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/uni/ddp-uni.jpeg");
            cadeiraUni.setFoto_banner("https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/uni/banner-uni.jpeg");
            cadeiraUni.setDesc_encosto(
                    "Encosto em tela flexível 100% poliéster de alta resistêcia. Apoio Lombar indepedente, regulável na altura");
            cadeiraUni.setDesc_apoio("Apoia braço regulável na altura.");
            cadeiraUni
                    .setDesc_rodinha("Rodizios / Rodinha com 55 mm de diâmetro indicado para qualquer tipom de piso.");
            cadeiraUni.setDesc_ajuste_altura(
                    "Mecanismo prático, permite ajuste de altura assento e inclinação do encosto");
            cadeiraUni.setDesc_revestimento("Revestimento do assento em crepe e encosto em tela de alta performance.");

            List<Categoria> categoriasUni = new ArrayList<>();
            categoriasUni.add(new Categoria(UUID.randomUUID(), "Contemporânea"));
            cadeiraUni.setCategorias(categoriasUni);

            List<Cor> coresUni = new ArrayList<>();
            coresUni.add(new Cor(UUID.randomUUID(), "Preto", "#0D0D0D",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/uni/cadeira-uni-all-black.jpeg"));
            coresUni.add(new Cor(UUID.randomUUID(), "Vermelho", "#730925",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/uni/cadeira-uni-all-red.jpeg"));
            coresUni.add(new Cor(UUID.randomUUID(), "Amarelo", "#F2B872",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/uni/cadeira-uni-all-yellow.jpeg"));
            coresUni.add(new Cor(UUID.randomUUID(), "Jeans", "#5A6473",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/uni/cadeira-uni-jeans.jpeg"));
            cadeiraUni.setCores(coresUni);

            Cadeira cadeiraLed = new Cadeira();

            cadeiraLed.setNome("Cadeira Led");
            cadeiraLed.setDescricao(
                    "Cadeira Led Lipstick Red / Flexform Tela em nylon. Assento revestido na cor preto. Apoia braços na cor preto e base Carbon Grey (Grafite).");
            cadeiraLed.setInformacoes(
                    "Desenvolvida pelos renomados designers Baldanzi & Novelli, a cadeira LED possui um design particular marcado pelas linhas retas, além de proporcionar ao usuário conforto e liberdade de movimentos.");
            cadeiraLed.setTemp_garantia(5);
            cadeiraLed.setPreco(1999.00f);
            cadeiraLed.setDimensoes("120cm x 70cm x 50cm");
            cadeiraLed.setFoto_dimensoes("https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/led/ddp-led.jpeg");
            cadeiraLed.setFoto_banner("https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/led/banner-led.jpeg");
            cadeiraLed.setDesc_encosto(
                    "Encosto revestido em tela flexível com apoio lombar indepedente regulável na altura.");
            cadeiraLed.setDesc_apoio(
                    "Apoia braços 4D regulável na altura, profundidade, abertura e ângulo. Parte superior em soft touch.");
            cadeiraLed
                    .setDesc_rodinha("Rodizios / Rodinha com 65 mm de diâmetro indicado para qualquer tipom de piso.");
            cadeiraLed.setDesc_ajuste_altura(
                    "Mecanismo ajuste de altura do assento. Inclinação do encosto com 4 pontos de parada, possui movemento relax.");
            cadeiraLed.setDesc_revestimento("Revestimento do assento em crepe e encosto em tela de alta performance.");

            List<Categoria> categoriasLed = new ArrayList<>();
            categoriasLed.add(new Categoria(UUID.randomUUID(), "Minimalista"));
            cadeiraLed.setCategorias(categoriasLed);

            List<Cor> coresLed = new ArrayList<>();
            coresLed.add(new Cor(UUID.randomUUID(), "Preto", "#0D0D0D",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/led/cadeira-led-all-black.jpeg"));
            coresLed.add(new Cor(UUID.randomUUID(), "Vermelho / Preto", "#731F1F",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/led/cadeira-led-shadow-black.jpeg"));
            coresLed.add(new Cor(UUID.randomUUID(), "Preto / Vermelho", "#A63352",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/led/cadeira-led-lipstick-red.jpeg"));
            coresLed.add(new Cor(UUID.randomUUID(), "Branco / Preto", "#F0F0F2",
                    "https://flex360-fotos-0w8djdm.s3.us-east-1.amazonaws.com/led/cadeira-led-black-n-white.jpeg"));
            cadeiraLed.setCores(coresLed);

        }

    }

}