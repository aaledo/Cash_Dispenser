package cajeroV4.vista;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class DisposicionUnaColumna implements LayoutManager	{

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void layoutContainer(Container miContenedor) {
		// TODO Auto-generated method stub

		
		//	Centramos horizontalmente los botones a visualizar :
		int x=miContenedor.getWidth()/2 -85;
		//	Indicamos primera línea de los botones a visualizar :		
		int y = 30;	
		
		int n=miContenedor.getComponentCount();		//	nro de componentes en total que tiene el contenedor
		for (int i=0; i<n; i++)	{
			Component c = miContenedor.getComponent(i);
			c.setBounds(x, y, 170, 20);
			y+=40;
			}
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		// TODO Auto-generated method stub
		
	}
		
}
