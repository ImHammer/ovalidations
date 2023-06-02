# OValidations
Uma lib que adiciona formas de validação de DTO's em Objects do Java

> JDK Version: 17

## Sobre
Baseado em alguns packages usados no Node, eu tomei a iniciativa de criar essa lib para o JDK, onde ela lhe dá a possibilidade de criar validações de valores de propriedades de um objeto (mesmo a propriedade estando privada, porém, recomendo deixar as propriedades protegidas ou publicas, de melhor modo seria criar a validação dentro do próprio objeto e o colocando em um metodo para retorno de boleano, ou do que preferir)

## Como usar?
é bem simples e prático, apesar de ainda não estar totalmente comentado

### > Para validação de apenas um valor de uma variavel, prefira utilizar o ValidationField.

- Validação com retorno boleano (true/false)
```Java
String tooBar = "";
ValidationField field = ValidationField.string().nonBlank("TooBar is blank").validate(tooBar);

if (field.canError(ValidationType.NONBLANK)) {
    // ERROR CODE HERE... 
} else {
    // NOTHING
}
```

- Validação com exceção (ValidationErrorException)
```Java
String tooBar = "";
try {
    ValidationField field = ValidationField.string().nonBlank("TooBar is blank").validateException(tooBar);
} catch (ValidationErrorException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```
### > Para validação varias propriedades dentro de um Object, será necessário um pouco mais de entendimento

- Validação com multiplos retornos
```Java
Object object;

ValidationObject validation = new ValidationObject();
validation.string("{PropertyName}").nonBlank("{PropertyName} is null!");

List<ValidationErrorException> errors = validation.validateReturn(object);
```
Os erros vem dentro de um List do java, e cada objeto dentro do list é uma exception que seria disparada, mas foi guardada e retornada pelo list (Pq? simplesmente para não precisar de uma classe a mais, e as exceptions tambem guardam informações necessárias!)

### > Para validação varias propriedades dentro de um Object, será necessário um pouco mais de entendimento

- Validação Exceção
```Java
Object object;

ValidationObject validation = new ValidationObject();
validation.string("{PropertyName}").nonBlank("{PropertyName} is null!");

try {
    validation.validateException(object);
} catch (IllegalArgumentException | IllegalAccessException | ValidationFieldErroException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```

Nesse caso o primeiro error será disparado como uma exceção (Não há necessidade de disparar todos os erros quando já se tem um metodo que retorna todos eles, basta usar o metodo 1)

NOTA: Nos dois casos, substitua o {PropertyName} pelo nome da propriedade que esta dentro do objeto, ainda é necessário que o nome seja exato, mas em breve trago atualizações...

- No momento há apenas dois tipos de validação, String e Number, que podem ser obtidos das seguintes formas:

```java
/// USANDO APENAS UM CAMPO
// PARA VALIDAÇÃO DO TIPO String
ValidationField field = ValidationField.string()...;
// TIPO Integer, Long, ...
ValidationField field = ValidationField.number()...;

field.validate();

/// USANDO OBJETO

ValidationObject validation = new ValidationObject();
// String
validation.string("propertyName")...;
// Integer...
validation.number("propertyname")...;

validation.validate();
```

### Conclusão
Esta lib foi criada inicialmente, para uso pessoal porém resolvi publicala no para quem mais precisar (Não sei se alguém realmente vai usar isso, não é de nível profissional) mas quem usar, espero que tenha lhe ajudado!

### Features
- Adicionar Validação para emails, password, etc... Um meio de rapidez para os desenvolvedores
- Mudar esquemas de validação para algo mais simples, ainda não pensei exatamente sobre.