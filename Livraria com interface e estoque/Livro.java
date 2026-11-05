public abstract class Livro implements Produto, Promocional {

	private String titulo;
	private String isbn;
	private final Autor autor;
	private double valor;
	
	public Livro(Autor autor) {
		this.autor = autor;
	}
	
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	
	public String getTitulo()
	{
		return titulo;
	}
	
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}
	
	public String getIsbn()
	{
		return isbn;
	}
	
	public void setValor(double valor)
	{
		this.valor = valor;
	}
	
	@Override
	public double getValor()
	{
		return valor;
	}
	
	public Autor getAutor()
	{
		return autor;
	}
	
	public void mostrarDetalhes() {
		System.out.println("--");

		System.out.println("Título: " + titulo);
		System.out.println("Valor: " + valor);
		System.out.println("ISBN: " + isbn);
	}
	
	@Override
	public String toString()
	{
		return String.format("%s", titulo);
	}
}



