@Test
public void listaAlugueisTest() {
    // Arrange
    Carro carroMock = new Carro();
    carroMock.setKm(12345);
    carroMock.setModelo("Sedan X");

    Cliente clienteMock = new Cliente();
    clienteMock.setNome("Cliente Teste");
    clienteMock.setTelefone("+55(11)91234-5678");

    Date dataAluguel = new Date(1704067200000L); // 01/01/2024
    Date dataDevolucao = new Date(1704326400000L); // 04/01/2024

    Aluguel aluguelMock = new Aluguel();
    aluguelMock.setId(1L);
    aluguelMock.setPago(false);
    aluguelMock.setDataAluguel(dataAluguel);
    aluguelMock.setDataDevolucao(dataDevolucao);
    aluguelMock.setValor(new BigDecimal("750"));
    aluguelMock.setCliente(clienteMock);
    aluguelMock.setCarro(carroMock);

    when(aluguelRepository.findAll()).thenReturn(List.of(aluguelMock));

    // Act
    List<ListarAlugueisQueryResultItem> result = listarAlugueisQuery.execute();

    // Assert
    Assertions.assertEquals(1, result.size());
    ListarAlugueisQueryResultItem item = result.get(0);

    Assertions.assertEquals(1L, item.id());
    Assertions.assertEquals("NAO", item.pago());
    Assertions.assertEquals("Sedan X", item.modelo());
    Assertions.assertEquals(12345, item.km());
    Assertions.assertEquals("Cliente Teste", item.nomeCliente());
    Assertions.assertEquals("+55(11)91234-5678", item.telefoneCliente());
    Assertions.assertEquals("750", item.valor());

    Mockito.verify(aluguelRepository, Mockito.times(1)).findAll();
}
