# Este archivo tiene de finalidad crear datos para ser usados como pruebas, sin mucho pulir
# Estoy algo oxidado en python, probablemente hayan cosas horribles entremedio
# Para añadir nombres y apellidos, agregar a lista_nombres_unclean y lista_apellidos_unclean
# Se limpian tildes y caracteres raros.
import random
from unidecode import unidecode # pip install unidecode  # para quitar tildes y cosas

def crear_correo(nombre, apellido):
    # Para nombres cortos
    if len(str(nombre)) <= 4 or len(str(apellido)) <= 4:
        if random.randint(1,2) == 2:
            return(str(nombre) + "." + str(apellido) + "@correo.com")
        return(str(nombre) + "." + str(apellido) + str(random.randint(1, 2003)) + "@correo.com")
    # Para nombres largos, 3 car iniciales, 3 car de apellido
    if random.randint(1,2) == 2:
            return(str(nombre)[:4] + "." + str(apellido)[:3] + str(random.randint(1, 2003)) + "@correo.com")
    return(str(nombre)[:3] + "." + str(apellido)[:3] + str(random.randint(1, 2003)) + "@correo.com")

# Quiero dejar constancia de la infinita pereza que es generar un rut y cuanto autodesprecio me motivó a hacer todo esto
def generar_rut_valido():
    # cuerpo del rut entre 10 y 26 millones que es un adulto a 2026
    numero = random.randint(10000000, 26000000) 
    numero_alreves = str(numero)[::-1]
    multiplicar = [2, 3, 4, 5, 6, 7]  # NO CONFUNDIR CON MULTIPLICADOR DIOOSS SANTOOAA AAA A
    suma = 0
    # Preparativos definidos zzz
    for i, digito in enumerate(numero_alreves):
        multiplicador  = multiplicar[ i % len(multiplicar)] # Es una función, da igual que se reemplace infinitamente
        suma += int(digito) * multiplicador
    
    resto = suma % 11
    digito_verificador_resultado = 11 - resto
    if digito_verificador_resultado == 11:
        digito_verificador = "0"
    elif digito_verificador_resultado == 10:
        digito_verificador = "K"
    else:
        digito_verificador = str(digito_verificador_resultado)

    rut = (f"{numero:,}-{digito_verificador}".replace(",", "."))
    return rut
# print(generar_rut_valido())

def generar_contrasena(num): #num es la longitud del texto
    numeros = "0123456789" 
    letras = "abcdefghijklmnopqrstuvwxyz"
    contrasena = ""
    for x in range(num):
        if random.randint(1, 2) == 1:
            contrasena += str(numeros[random.randint(0, len(numeros) - 1)])
        elif random.randint(1, 2) == 2:
            if random.randint(1,2) == 1:
                contrasena += letras[random.randint(0, len(letras) - 1)]
            contrasena += letras[random.randint(0, len(letras) - 1)].upper()
    return(contrasena)
# Sé que se pueden hacer en solo dos lineas, pero no me gusta dejar tan al aire el rng.

class Usuario: # Quizás sea de utilidad más adelante
    
    def __init__(self, nombre, apellido):      
        self.nombre_completo = str(nombre) + " " + str(apellido)
        self.nombre = str(nombre)
        self.apellido = str(apellido)
        self.correo = str(crear_correo(nombre, apellido))
        self.saldo = random.randint(10, 99999999)
        self.saldo_moneda = ""
        rut_sin_validar = generar_rut_valido()
        self.rut = rut_sin_validar.replace(".", "")
        self.contrasena = generar_contrasena(random.randint(9, 15)) # entre 8 y 16 caracteres
        self.banco = ""
        self.apodo = ""

    def asignar_moneda(self, lista_de_monedas): # Asigna el tipo de moneda de usuario 65% de que sea clp
        if random.randint(1, 100) <= 65:
            self.saldo_moneda = "CLP_ID"
        else:
            self.saldo_moneda = lista_de_monedas[random.randint(0, len(lista_de_monedas) - 1)]
        return self.saldo_moneda
    
    def generar_apodo(self):
        numgen = random.randint(1, 3)
        self.apodo = (str(self.nombre[:numgen] + self.apellido[:numgen]))
        return(self.apodo)

    def dar_banco(self, lista_bancos):
        self.banco = lista_bancos[random.randint(0, len(lista_bancos) - 1)]
        return(self.banco)

    def saludacion(self):
         print("Hola, yo soy: " + self.nombre_completo + " y mi correo es " + self.correo + ". Tengo " + str(self.dinero) + " en el banco:). Mi rut es: " + self.rut)
        
# Usuario.saludacion() devuelve un saludo diciendo su nombre, apellido, correo y monto. Fines de testeo
# y una chance random de tener o no numero para dar aire de realismo
# Lista con nombres, pedida a ser generada aleatoriamente a gemini con el prompt: "Genera una lista de 60 nombres aleatorios para ser usada el python"
lista_nombres_unclean = [
    "Alejandro", "Sofia", "Mateo", "Valentina", "Santiago", 
    "Isabella", "Sebastian", "Camila", "Leonardo", "Valeria",
    "Diego", "Mariana", "Nicolas", "Gabriela", "Samuel", 
    "Daniela", "Joaquin", "Sara", "Tomas", "Victoria",
    "Lucas", "Lucia", "Benjamin", "Natalia", "Matias", 
    "Elena", "Francisco", "Catalina", "Javier", "Fernanda",
    "Bruno", "Paula", "Emiliano", "Carolina", "Gabriel", 
    "Andrea", "Thiago", "emilia", "Manuel", "Alejandra",
    "adrian", "Martina", "Julian", "Clara", "alvaro", 
    "julia", "Gael", "Alondra", "Rodrigo", "Camilo",
    "claudia", "Enrique", "Juana", "Iván", "Beatriz", 
    "Galo", "Olga", "Marcos", "Agustina", "Agustín" ]
# La lista de nombres fue generada de igual forma, preguntando por apellidos en chile.
lista_apellidos_unclean = [
    "González", "Muñoz", "Rojas", "Díaz", "Pérez",
    "Soto", "Contreras", "Silva", "Martínez", "Sepúlveda",
    "Morales", "Rodríguez", "López", "Fuentes", "Hernández",
    "Torres", "Araya", "Flores", "Espinoza", "Valenzuela",
    "Castillo", "Tapia", "Reyes", "Gutiérrez", "Castro",
    "Pizarro", "Álvarez", "Vásquez", "Sánchez", "Fernández",
    "Carrasco", "Gómez", "Cortés", "Herrera", "Núñez",
    "Jara", "Vergara", "Rivera", "Figueroa", "Miranda",
    "Bravo", "Vera", "Molina", "Vega", "Campos",
    "Sandoval", "Orellana", "Cárdenas", "Zúñiga", "Alarcón",
    "Garrido", "Ortiz", "Maldonado", "Henríquez", "Saavedra",
    "Palacios", "Lagos", "Poblete", "Bustos", "Mendoza"
]
lista_nombres = []
lista_apellidos = []
lista_bancos = ['Master PLOP', "Banco estafo", "Bacno de Chile", "International BANK", "Banco Eduardo", "Banco Santoentender", "Banco", "Scott tia Bank", "Banco itaes", "Banco Fall Bellas", "Banca Parasentar"]
monedas_id_lista = ['USD_ID', 'CLP_ID', 'ARS_ID', 'CAD_ID', 'AUD_ID', 'MXN_ID', 'BRL_ID', 'COP_ID', 'PEN_ID', 'VES_ID', 'UYU_ID', 'EUR_ID', 'GBP_ID', 'JPY_ID', 'CNY_ID', 'RUB_ID', 'CVE_ID']


for nombre in lista_nombres_unclean: # esto le quita tildez y caracteres raros
    lista_nombres.append(unidecode(nombre))
for apellido in lista_apellidos_unclean:  # esto le quita tildez y caracteres raros
    lista_apellidos.append(unidecode(apellido))


'''   # Test de Usuario(self)
aa = random.randint(1, 60)
test1 = Usuario(lista_nombres[aa], lista_apellidos[aa])

print(test1.nombre_completo + "  " + test1.correo)  # FUNCIONA
print(test1.saludacion())
'''

lista_de_usuarios = []
'''
for x in range(50):  # rango  es el tamaño de la lista de usuarios a crear
    num_nom = random.randint(0, len(lista_nombres) - 1)
    num_ape = random.randint(0, len(lista_apellidos) - 1)
    num_ape2 = random.randint(0, len(lista_apellidos) - 1)
    lista_de_usuarios.append(Usuario(lista_nombres[num_nom], str(lista_apellidos[num_ape] + " " + lista_apellidos[num_ape2])))
'''
for x in range(50):  # rango  es el tamaño de la lista de usuarios a crear
    num_nom = random.randint(0, len(lista_nombres) - 1)
    num_ape = random.randint(0, len(lista_apellidos) - 1)
    num_ape2 = random.randint(0, len(lista_apellidos) - 1)
    persona = Usuario(lista_nombres[num_nom], str(lista_apellidos[num_ape] + " " + lista_apellidos[num_ape2]))
    persona.dar_banco(lista_bancos)
    persona.asignar_moneda(monedas_id_lista)
    lista_de_usuarios.append(persona)



# Crea usuarios con 1 nombre, rut, correo y 2 apellidos




# crear inyeccion para sql.  ('12345678-9', 'Alejandro', 'Silva Torres', 'Ale.Sil11@correo.com', NULL, contrasena, saldo, moneda_ID)
inyeccion_de_usuarios = ""
for persona in lista_de_usuarios:
    inyeccion_de_usuarios += "('" + persona.rut + "', '" + persona.nombre + "', '" + persona.apellido + "', '" + persona.correo + "', NULL, '" + persona.contrasena + "', '" + str(persona.saldo)+ "', '" + persona.saldo_moneda +  "'),\n"


# inyeccion para contactos sql. (origen_rut, nombre_destinatario, alias, banco_destino, numero_cuenta, contacto_rut)
inyeccion_de_contactos = ""
for persona_origen in lista_de_usuarios:
    # lista_de_usuarios[random.randint(1, len(lista_de_usuarios) - 1)]
    cantidad_de_contactos = random.randint(0, 15)  # el numero random es la cantidad maxima de contactos para cada rut 
    for x in range(cantidad_de_contactos):
        persona = lista_de_usuarios[random.randint(1, len(lista_de_usuarios) - 1)]
        inyeccion_de_contactos += "('" + persona_origen.rut + "', '" + persona.nombre + " " + persona.apellido + "', '" + persona.generar_apodo() + "', '" + persona.banco + "', '" + str(random.randint(1000000, 99999999)) + "', '" + persona.rut + "'),\n"

# inyeccion de transacciones. (origen)
inyeccion_de_transacciones = ""
# (sender_id, reveiver_id, moneda_id, monto, tipo(+ o -), fecha

# premisa: No es necesario que transfiera a un contacto, ni haber transferido para guardarlo
for persona_origen in lista_de_usuarios:
    cantidad_de_transacciones = random.randint(1, 10)
    for x in range(cantidad_de_transacciones):
        suerte = str(1)
        if random.randint(1,2) == 1:
            suerte = str(0)
        persona = lista_de_usuarios[random.randint(1, len(lista_de_usuarios) - 1)]
        inyeccion_de_transacciones += "('" + persona_origen.rut + "', '" + persona.rut + "', '" + monedas_id_lista[random.randint(0, len(monedas_id_lista) - 1)] + "', '" + str(random.randint(1, 9999999)) + "', '" + suerte +  "'),\n"



print("------------------------------------")
print(inyeccion_de_usuarios)
print("------------------------------------")
print(inyeccion_de_contactos)
print("------------------------------------")
print(inyeccion_de_transacciones)
print("------------------------------------")




