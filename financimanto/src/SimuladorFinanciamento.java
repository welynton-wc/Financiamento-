import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class SimuladorFinanciamento extends JFrame {

    // Componentes do Veículo
    private JComboBox<String> cbMarca;
    private JTextField txtModelo;
    private JComboBox<Integer> cbAno;
    private JTextField txtValor;
    private JRadioButton rbNovo;
    private JRadioButton rbUsado;

    // Painel e campos para Veículo Usado
    private JPanel panelUsado;
    private JTextField txtQuilometragem;
    private JTextField txtProprietarios;

    // Componentes do Financiamento
    private JCheckBox chkPossuiEntrada;
    private JLabel lblEntrada;
    private JTextField txtEntrada;
    private JComboBox<Integer> cbParcelas;

    // Botões
    private JButton btnCalcular;
    private JButton btnLimpar;

    // Painel e labels de Resultado
    private JPanel panelResultado;
    private JLabel lblValorFinanciado;
    private JLabel lblValorParcela;
    private JLabel lblTotalPagar;

    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");

    public SimuladorFinanciamento() {
        super("Financiamento de Carros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 750);
        setLocationRelativeTo(null);

        // Container principal com Scroll para garantir boa exibição
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- 1. TÍTULO PRINCIPAL ---
        JLabel lblTitulo = new JLabel("Financiamento de Carros");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- 2. DADOS DO VEÍCULO ---
        JPanel panelVeiculo = new JPanel(new GridBagLayout());
        panelVeiculo.setBorder(BorderFactory.createTitledBorder("Dados do Veículo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Marca (Lista de seleção única)
        gbc.gridx = 0; gbc.gridy = 0;
        panelVeiculo.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cbMarca = new JComboBox<>(new String[]{"FIAT", "Volkswagen", "Chevrolet", "Ford", "Toyota", "Honda", "Peugeot"});
        panelVeiculo.add(cbMarca, gbc);

        // Modelo (Texto livre)
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelVeiculo.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtModelo = new JTextField(15);
        panelVeiculo.add(txtModelo, gbc);

        // Ano (2026 até 2000 em ordem decrescente)
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelVeiculo.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cbAno = new JComboBox<>();
        for (int ano = 2026; ano >= 2000; ano--) {
            cbAno.addItem(ano);
        }
        panelVeiculo.add(cbAno, gbc);

        // Valor (Texto livre)
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelVeiculo.add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtValor = new JTextField(15);
        panelVeiculo.add(txtValor, gbc);

        // Tipo (Novo / Usado)
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panelVeiculo.add(new JLabel("Tipo:"), gbc);

        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rbNovo = new JRadioButton("NOVO");
        rbUsado = new JRadioButton("USADO");
        ButtonGroup bgTipo = new ButtonGroup();
        bgTipo.add(rbNovo);
        bgTipo.add(rbUsado);
        panelTipo.add(rbNovo);
        panelTipo.add(rbUsado);

        gbc.gridx = 1; gbc.weightx = 1.0;
        panelVeiculo.add(panelTipo, gbc);

        mainPanel.add(panelVeiculo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- 3. DADOS DO VEÍCULO USADO ---
        panelUsado = new JPanel(new GridBagLayout());
        panelUsado.setBorder(BorderFactory.createTitledBorder("Dados do Veículo Usado"));

        GridBagConstraints gbcUsado = new GridBagConstraints();
        gbcUsado.insets = new Insets(5, 5, 5, 5);
        gbcUsado.fill = GridBagConstraints.HORIZONTAL;

        gbcUsado.gridx = 0; gbcUsado.gridy = 0;
        panelUsado.add(new JLabel("Quilometragem:"), gbcUsado);
        gbcUsado.gridx = 1; gbcUsado.weightx = 1.0;
        txtQuilometragem = new JTextField(15);
        panelUsado.add(txtQuilometragem, gbcUsado);

        gbcUsado.gridx = 0; gbcUsado.gridy = 1; gbcUsado.weightx = 0;
        panelUsado.add(new JLabel("Proprietários:"), gbcUsado);
        gbcUsado.gridx = 1; gbcUsado.weightx = 1.0;
        txtProprietarios = new JTextField(15);
        panelUsado.add(txtProprietarios, gbcUsado);

        panelUsado.setVisible(false); // Oculto por padrão
        mainPanel.add(panelUsado);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- 4. FINANCIAMENTO ---
        JPanel panelFinanciamento = new JPanel(new GridBagLayout());
        panelFinanciamento.setBorder(BorderFactory.createTitledBorder("Financiamento"));
        GridBagConstraints gbcFin = new GridBagConstraints();
        gbcFin.insets = new Insets(5, 5, 5, 5);
        gbcFin.fill = GridBagConstraints.HORIZONTAL;

        chkPossuiEntrada = new JCheckBox("Possui entrada?");
        gbcFin.gridx = 0; gbcFin.gridy = 0; gbcFin.gridwidth = 2;
        panelFinanciamento.add(chkPossuiEntrada, gbcFin);

        lblEntrada = new JLabel("Entrada:");
        lblEntrada.setVisible(false);
        gbcFin.gridx = 0; gbcFin.gridy = 1; gbcFin.gridwidth = 1;
        panelFinanciamento.add(lblEntrada, gbcFin);

        txtEntrada = new JTextField(15);
        txtEntrada.setVisible(false);
        gbcFin.gridx = 1; gbcFin.gridy = 1; gbcFin.weightx = 1.0;
        panelFinanciamento.add(txtEntrada, gbcFin);

        gbcFin.gridx = 0; gbcFin.gridy = 2; gbcFin.weightx = 0;
        panelFinanciamento.add(new JLabel("Parcelas:"), gbcFin);

        cbParcelas = new JComboBox<>(new Integer[]{12, 24, 36, 48, 60});
        gbcFin.gridx = 1; gbcFin.gridy = 2; gbcFin.weightx = 1.0;
        panelFinanciamento.add(cbParcelas, gbcFin);

        mainPanel.add(panelFinanciamento);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- 5. BOTÕES DE AÇÃO ---
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnCalcular = new JButton("CALCULAR");
        btnLimpar = new JButton("LIMPAR");
        panelBotoes.add(btnCalcular);
        panelBotoes.add(btnLimpar);

        mainPanel.add(panelBotoes);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- 6. PAINEL DE RESULTADO ---
        panelResultado = new JPanel();
        panelResultado.setLayout(new BoxLayout(panelResultado, BoxLayout.Y_AXIS));
        panelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

        lblValorFinanciado = new JLabel("Valor financiado: R$ 0,00");
        lblValorParcela = new JLabel("Valor da parcela: R$ 0,00");
        lblTotalPagar = new JLabel("Total a pagar: R$ 0,00");

        lblValorFinanciado.setFont(new Font("Arial", Font.BOLD, 14));
        lblValorParcela.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalPagar.setFont(new Font("Arial", Font.BOLD, 14));

        panelResultado.add(lblValorFinanciado);
        panelResultado.add(Box.createRigidArea(new Dimension(0, 5)));
        panelResultado.add(lblValorParcela);
        panelResultado.add(Box.createRigidArea(new Dimension(0, 5)));
        panelResultado.add(lblTotalPagar);

        panelResultado.setVisible(false); // Oculto até que seja calculado
        mainPanel.add(panelResultado);

        // Adiciona ScrollPane para garantir responsividade em telas menores
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane);

        // --- EVENTOS E LOGICA DA INTERFACE ---

        // Alternar visibilidade do painel de Veículo Usado
        ActionListener tipoVeiculoListener = e -> {
            boolean isUsado = rbUsado.isSelected();
            panelUsado.setVisible(isUsado);
            revalidate();
            repaint();
        };
        rbNovo.addActionListener(tipoVeiculoListener);
        rbUsado.addActionListener(tipoVeiculoListener);

        // Alternar visibilidade do campo Entrada
        chkPossuiEntrada.addActionListener(e -> {
            boolean temEntrada = chkPossuiEntrada.isSelected();
            lblEntrada.setVisible(temEntrada);
            txtEntrada.setVisible(temEntrada);
            if (!temEntrada) {
                txtEntrada.setText("");
            }
            revalidate();
            repaint();
        });

        // Evento do Botão Limpar
        btnLimpar.addActionListener(e -> limparFormulario());

        // Evento do Botão Calcular
        btnCalcular.addActionListener(e -> calcularFinanciamento());
    }

    private void calcularFinanciamento() {
        // Validações dos campos
        if (txtModelo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o modelo do veículo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtValor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o valor do veículo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!rbNovo.isSelected() && !rbUsado.isSelected()) {
            JOptionPane.showMessageDialog(this, "Selecione se o veículo é Novo ou Usado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double valorVeiculo;
        try {
            valorVeiculo = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor do veículo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rbUsado.isSelected()) {
            if (txtQuilometragem.getText().trim().isEmpty() || txtProprietarios.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha a quilometragem e o número de proprietários para veículos usados.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        double valorEntrada = 0.0;
        if (chkPossuiEntrada.isSelected()) {
            if (txtEntrada.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o valor da entrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                valorEntrada = Double.parseDouble(txtEntrada.getText().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor de entrada inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (valorEntrada >= valorVeiculo) {
                JOptionPane.showMessageDialog(this, "O valor de entrada deve ser menor que o valor do veículo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        int numParcelas = (Integer) cbParcelas.getSelectedItem();

        // Fórmula de financiamento conforme instrução:
        // valor_financiado = valor_veiculo - entrada
        // valor_total = valor_financiado * (1 + taxa)  -> (Usando taxa base simples de 32% / 0.32)
        // valor_parcela = valor_total / numero_parcelas
        // total_a_pagar = valor_parcela * numero_parcelas
        double taxa = 0.32;
        double valorFinanciado = valorVeiculo - valorEntrada;
        double valorTotal = valorFinanciado * (1 + taxa);
        double valorParcela = valorTotal / numParcelas;
        double totalAPagar = valorParcela * numParcelas;

        // Atualizar resultados na tela
        lblValorFinanciado.setText("Valor financiado: " + df.format(valorFinanciado));
        lblValorParcela.setText("Valor da parcela: " + df.format(valorParcela));
        lblTotalPagar.setText("Total a pagar: " + df.format(totalAPagar));

        panelResultado.setVisible(true);
        revalidate();
        repaint();
    }

    private void limparFormulario() {
        cbMarca.setSelectedIndex(0);
        txtModelo.setText("");
        cbAno.setSelectedIndex(0);
        txtValor.setText("");

        rbNovo.setSelected(false);
        rbUsado.setSelected(false);

        txtQuilometragem.setText("");
        txtProprietarios.setText("");
        panelUsado.setVisible(false);

        chkPossuiEntrada.setSelected(false);
        lblEntrada.setVisible(false);
        txtEntrada.setText("");
        txtEntrada.setVisible(false);
        cbParcelas.setSelectedIndex(0);

        panelResultado.setVisible(false);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimuladorFinanciamento().setVisible(true);
        });
    }
}