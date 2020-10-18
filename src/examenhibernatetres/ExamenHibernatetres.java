/*
Javier Montejo Examen Hibernate
*/

package examenhibernatetres;
/*
CREATE TABLE Equipos (
	codigo VARCHAR (12) PRIMARY KEY,
    nombre VARCHAR (40),
    ubicacion VARCHAR (30)
);

CREATE TABLE Personas (
	codigo VARCHAR (12) PRIMARY KEY,
    nombre VARCHAR (40),
    pass VARCHAR (30),
    email VARCHAR (30)
);

CREATE TABLE Avisos (
    fechaInicio TIMESTAMP,
    descripcion VARCHAR (50),
	codigo_persona VARCHAR (12),
    codigo_equipo VARCHAR (12),
    fechaFin TIMESTAMP,
    detalles TEXT,
    FOREIGN KEY (codigo_persona) REFERENCES Personas (codigo),
    FOREIGN KEY (codigo_equipo) REFERENCES Equipos (codigo),
    PRIMARY KEY (codigo_persona, codigo_equipo, fechaInicio) 
)
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExamenHibernatetres {
    
static Scanner sc = new Scanner(System.in);
static String nombre = "";
static String pass = "";

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger
                = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate")
                .setLevel(java.util.logging.Level.SEVERE);

        
        System.out.println("Dime el nombre: ");
        nombre = sc.nextLine();
        
        System.out.println("Dime el password: ");
        pass = sc.nextLine();
        
        if (nombre.equals("admin") && pass.equals("7890")) {
            mostrarMenuAdmin();
        }
        
        else {
            
            Session sesion =
        NewHibernateUtil.getSessionFactory().openSession();
        Query consulta = sesion.createQuery("from Libros");
        List resultados = consulta.list();
        
        for( Object resultado : resultados)
        {
            Personas persona = (Personas) resultado;

            if( persona.getNombre().equals(nombre) &&
                    persona.getPass().equals(pass)) {
                mostrarMenuUsuario();
                sesion.close(); 
            }
        
        }
        sesion.close(); 
        
            System.out.println("Usuario incorrecto");
        }
        
    }
    
    static void mostrarMenuUsuario() {
        boolean fin = false;
        System.out.println("1.Introducir incidencia");
        System.out.println("2.Consultar avisos");
        System.out.println("0.Salir");
        String opcion = sc.nextLine();
        
        do {
            switch (opcion) {
                case "1":
                    anyadirIncidencia();
                    break;


                case "2":
                    buscarIncidenciaUsuario();
                break;
                
                case "0":
                    fin = true;
                break;
                
                default: System.out.println("Error");
                break;
            }
        }while (!fin);
        
    }
    
    static void mostrarMenuAdmin(){
        
        boolean fin = false;
        System.out.println("1.Añadir Usuario");
        System.out.println("2.Añadir equipo");
        System.out.println("3.Ver usuarios");
        System.out.println("4.Ver equipos");
        System.out.println("5.Editar incidencias");
        System.out.println("6.Ver incidencias");
        System.out.println("7.Buscar incidencia");
        System.out.println("0.Salir");
        String opcion = sc.nextLine();
        
        do {
            switch (opcion) {
                case "1":
                    anyadirUsuarios();
                    break;


                case "2":
                    anyadirEquipos();
                break;
                
                case "3":
                    verUsuarios();
                break;
                
                case "4":
                    verEquipos();
                break;
                
                case "5":
                    editarIncidencia();
                break;
                
                case "6":
                    verIncidencias();
                break;
                
                case "7":
                    buscarIncidencia();
                break;
                
                 case "0":
                    fin = true;
                break;
                
                default: System.out.println("Error");
                break;
            }
        }while (!fin);
        
    }
    
    static void anyadirIncidencia() {
        System.out.println("Introduzca el codigo del usuario: ");
        String codigo = sc.nextLine();
        System.out.println("Introduzca el codigo del equipo: ");
        String codEquipo = sc.nextLine();
        Date fechaActual = new Date();
        
        System.out.println("Introduzca la descripcion de la incidencia: ");
        String incidencia = sc.nextLine();
        
        Avisos aviso = new Avisos(new AvisosId(codigo, codEquipo, fechaActual),
                null, null, incidencia, null, null);
        Session sesion
                = NewHibernateUtil.getSessionFactory().openSession();
        Transaction trans = sesion.beginTransaction();
        sesion.save(aviso);
        trans.commit();
        sesion.close();
        System.out.println("Aviso añadido!");
    }
    
    static void buscarIncidenciaUsuario() {
        
        Session sesion
                = NewHibernateUtil.getSessionFactory().openSession();
        Query consulta = sesion.createQuery(
                "FROM Personas WHERE pass='" + pass + "'");
        List resultados = consulta.list();
        Personas personasMod = (Personas) resultados.get(0);
        
        String codigo = personasMod.getCodigo();
        
        Session sesion1
                = NewHibernateUtil.getSessionFactory().openSession();
        Query consulta1 = sesion1.createQuery(
                "from Avisos WHERE codigo_persona is " + codigo  );
        List resultados1 = consulta1.list();
        
        for (Object resultado : resultados1) {
            Avisos aviso = (Avisos) resultado;
            System.out.println(aviso.toString());
        }
        sesion.close();
        sesion1.close();
    }
    
    static void anyadirUsuarios () {
        
        System.out.println("Introduce el codigo del usuario: ");
        String codigo = sc.nextLine();
        System.out.println("Introduce el nombre del usuario: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce la contraseña del usuario: ");
        String pass = sc.nextLine();
        System.out.println("Introduce el email del usuario: ");
        String email = sc.nextLine();
        
        Personas usuario = new Personas(codigo, nombre,pass,
                email,null);
        Session sesion
                = NewHibernateUtil.getSessionFactory().openSession();
        Transaction trans = sesion.beginTransaction();
        sesion.save(usuario);
        trans.commit();
        sesion.close();
        System.out.println("Usuario añadido!");
    }
    
    static void anyadirEquipos () {
        
        System.out.println("Introduce el codigo del equipo: ");
        String codigo = sc.nextLine();
        System.out.println("Introduce el nombre del equipo: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce la ubicacion del equipo: ");
        String ubicacion = sc.nextLine();
        

        Equipos equipo = new Equipos(codigo, nombre,
                ubicacion,null);
        Session sesion
                = NewHibernateUtil.getSessionFactory().openSession();
        Transaction trans = sesion.beginTransaction();
        sesion.save(equipo);
        trans.commit();
        sesion.close();
        System.out.println("Vehiculo añadido!");
    }
    
    static void verUsuarios () {
        
        System.out.println("Inserte un nombre para buscar coincidencias: ");
        String nombre = sc.nextLine().toLowerCase();
        Session sesion
                = NewHibernateUtil.getSessionFactory().openSession();
        Query consulta = sesion.createQuery(
                "from Personas WHERE LOWER(nombre) LIKE '%" + nombre + "%'");
        List resultados = consulta.list();
        for (Object resultado : resultados) {
            Personas usuario = (Personas) resultado;
            System.out.println(usuario.toString());
        }
        sesion.close();
    }
    
    static void verEquipos () {
        
        System.out.println("Inserte un nombre para buscar coincidencias: ");
        String nombre = sc.nextLine().toLowerCase();
        Session sesion
                = NewHibernateUtil.getSessionFactory().openSession();
        Query consulta = sesion.createQuery(
                "from Equipo WHERE LOWER(nombre) LIKE '%" 
                        + nombre + "%' OR WHERE LOWER (codigo) is "
        + nombre);
        List resultados = consulta.list();
        for (Object resultado : resultados) {
            Equipos equipo = (Equipos) resultado;
            System.out.println(equipo.toString());
        }
        sesion.close();
        
        
    }
    
    static void editarIncidencia () {
        
        System.out.println("Indica el codigo del equipo: ");
        String codigo = sc.nextLine();
        
         Session sesion1
                = NewHibernateUtil.getSessionFactory().openSession();
         
        Query consulta1 = sesion1.createQuery(
                "from Avisos WHERE codigo_equipo is " + codigo  );
        List resultados1 = consulta1.list();
        
        for (Object resultado : resultados1) {
            Avisos aviso = (Avisos) resultado;
            System.out.println(aviso.toString());
        }
        
        sesion1.close();
    }
    
    static void verIncidencias() {
        Session sesion
                = NewHibernateUtil.getSessionFactory().openSession();
        Query consulta = sesion.createQuery(
                "from Avisos WHERE fechaFin is null");
        List resultados = consulta.list();
        
        for (Object resultado : resultados) {
            
            Avisos aviso = (Avisos) resultado;
            System.out.println(aviso.toString());
        }
        
        sesion.close();
    }
    
    static void buscarIncidencia () {
        
        System.out.println("Introduce el texto: ");
        String texto = sc.nextLine();
        
        Session sesion1
                = NewHibernateUtil.getSessionFactory().openSession();
        Query consulta1 = sesion1.createQuery(
                "from Avisos WHERE LOWER(descripcion) LIKE '%" + texto + "%'");
        List resultados1 = consulta1.list();
        
        for (Object resultado : resultados1) {
            Avisos aviso = (Avisos) resultado;
            System.out.println(aviso.toString());
        }
       
        sesion1.close();
    }
}
