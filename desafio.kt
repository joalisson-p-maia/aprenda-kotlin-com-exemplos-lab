import java.util.UUID

enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

data class Usuario(
    val id: String,
    val nome: String,
    val idade: Int
) {
    init {
        require(nome.isNotBlank()) { "O nome do usuário não pode estar vazio." }
        require(idade > 0) { "Idade deve ser maior que zero." }
    }
}

data class ConteudoEducacional(
    val id: String,
    var nome: String,
    val duracaoMinutos: Int = 60
) {
    init {
        require(nome.isNotBlank()) { "Nome do conteúdo não pode estar vazio." }
        require(duracaoMinutos > 0) { "Duração deve ser maior que zero." }
    }
}

class Formacao(
    val id: String,
    val nome: String,
    val nivel: Nivel,
    private val conteudos: MutableList<ConteudoEducacional> = mutableListOf()
) {
    private val inscritos = mutableListOf<Usuario>()

    init {
        require(nome.isNotBlank()) { "O nome da formação não pode estar vazio." }
    }

    fun adicionarConteudo(conteudo: ConteudoEducacional) {
        conteudos.add(conteudo)
    }

    fun listarConteudos(): List<ConteudoEducacional> = conteudos.toList()

    fun matricular(usuario: Usuario) {
        if (inscritos.any { it.id == usuario.id }) {
            println("Usuário ${usuario.nome} já está matriculado.")
            return
        }

        inscritos.add(usuario)
        println("Usuário ${usuario.nome} matriculado com sucesso!")
    }

    fun cancelarMatricula(usuarioId: String) {
        val removido = inscritos.removeIf { it.id == usuarioId }
        if (removido)
            println("Matrícula removida com sucesso.")
        else
            println("Usuário não encontrado na formação.")
    }

    fun listarInscritos(): List<Usuario> = inscritos.toList()

    fun cargaHorariaTotal(): Int = conteudos.sumOf { it.duracaoMinutos }

    fun resumo() {
        println("=== Formação: $nome ===")
        println("Nível: $nivel")
        println("Conteúdos: ${conteudos.size}")
        println("Carga horária total: ${cargaHorariaTotal()} minutos")
        println("Inscritos: ${inscritos.size}")
        println("=========================")
    }
}

fun gerarUUID(): String{
    return UUID.randomUUID().toString()
}

fun main() {

    val introKotlin = ConteudoEducacional(gerarUUID(),"Introdução ao Kotlin", 90)
    val poo = ConteudoEducacional(gerarUUID(),"Programação Orientada a Objetos", 120)

    val formacaoKotlin = Formacao(
        id = gerarUUID(),
        nome = "Formação Kotlin Developer",
        nivel = Nivel.INTERMEDIARIO
    )

    formacaoKotlin.adicionarConteudo(introKotlin)
    formacaoKotlin.adicionarConteudo(poo)

    val jhon = Usuario(id = gerarUUID(), nome = "Jhon", idade = 25)
    val caio = Usuario(id = gerarUUID(), nome = "Caio", idade = 30)

    formacaoKotlin.matricular(jhon)
    formacaoKotlin.matricular(caio)

    formacaoKotlin.resumo()

    println("\nListando inscritos:")
    formacaoKotlin.listarInscritos().forEach {
        println("${it.nome} (${it.idade} anos)")
    }
}