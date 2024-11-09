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
        
        // Query para buscar todos os dados de receitas
        $sqlReceitas = "SELECT ID_RECEITA, ID_USUARIO, DESCRICAO_RECEITA, VALOR_RECEITA, DATA_RECEITA, RECEITA_IMAGEM 
                        FROM RECEITA";
        $stmtReceitas = $pdo->query($sqlReceitas);
        $receitas = $stmtReceitas->fetchAll();

        // Query para buscar todos os dados de despesas
       //  $sqlDespesas = "SELECT ID_DESPESA, ID_USUARIO, DESCRICAO_DESPESA, VALOR_DESPESA, STATUS_DESPESA, DATA_DESPESA, DESPESA_IMAGEM 
       //                 FROM DESPESA";
       //  $stmtDespesas = $pdo->query($sqlDespesas);
       //  $despesas = $stmtDespesas->fetchAll();

        // Enviar as receitas e despesas no formato JSON
        header('Content-Type: application/json');
        echo json_encode($receitas);
    
    } catch (PDOException $e) {
        echo "Erro de conexÃ£o: " . $e->getMessage();
        exit;
    }
    ?>