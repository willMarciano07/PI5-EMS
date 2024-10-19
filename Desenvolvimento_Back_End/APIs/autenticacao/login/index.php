<?php 
$host = 'www.thyagoquintas.com.br';
$db   = 'engenharia_03';
$user = 'engenharia_03';
$pass = 'tucanotoco';
$charset = 'utf8mb4';

$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];

try {
    $pdo = new PDO($dsn, $user, $pass, $options);


      // Ajuste para receber usuÃ¡rio e senha via GET (para simplificaÃ§Ã£o)
      $usuario = isset($_GET['usuario']) ? $_GET['usuario'] : '';
      $senha = isset($_GET['senha']) ? $_GET['senha'] : '';

      // Query para buscar usuÃ¡rio especÃ­fico
      $sql = "SELECT ID_LOGIN as id_Login, USUARIO_LOGIN as usuario_Login, USUARIO_SENHA as usuario_Senha FROM LOGIN WHERE USUARIO_LOGIN = :usuario AND USUARIO_SENHA = :senha";
      $stmt = $pdo->prepare($sql);
      $stmt->execute(['usuario' => $usuario, 'senha' => $senha]);

      $usuarios = $stmt->fetchAll();

      header('Content-Type: application/json');
      echo json_encode($usuarios);

  } catch (\PDOException $e) {
      echo "Erro de conexÃ£o: " . $e->getMessage();
      exit;
  }
  ?>
