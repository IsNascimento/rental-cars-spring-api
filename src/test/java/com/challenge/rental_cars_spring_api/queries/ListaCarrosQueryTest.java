@Test
public void listaCarrosTest() {
    // Arrange
    Carro carroMock = new Carro();
    carroMock.setId(123L);
    carroMock.setModelo("GOL");

    List<Carro> carros = List.of(carroMock);
    when(carroRepository.findAll()).thenReturn(carros);

    // Act
    List<ListarCarrosQueryResultItem> carrosResult = listarCarrosQuery.execute();

    // Assert
    Assertions.assertEquals(1, carrosResult.size(), "A lista deve conter exatamente um carro");

    ListarCarrosQueryResultItem carroResultItem = carrosResult.get(0);
    Assertions.assertEquals(123L, carroResultItem.id());
    Assertions.assertEquals("GOL", carroResultItem.modelo());

    Mockito.verify(carroRepository, Mockito.times(1)).findAll();
}
