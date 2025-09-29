----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/03/2025 11:57:25 PM
-- Design Name: 
-- Module Name: test_env - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity test_env is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC; --centru
           btnReset: in std_logic;--sus
           btnRegWrite: in std_logic;--jos
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (7 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
           
         

end test_env;

architecture Behavioral of test_env is

--declarare memorie ROM
type MEM_ROM is array(0 to 31) of std_logic_vector(31 downto 0);
signal ROM:MEM_ROM:=(
        X"00000820",--add
        X"8C020000",--lw
        X"20030001",--addi
        X"00002020",--add
       -- X"00002820",--add
        X"10410009",--beq
        X"8C660000",--lw
        X"30C70001",--andi
        X"14E00003",--bne
        X"00864022",--sub
        X"05000001",--bgez
        X"00C02020",--add
        X"20630001",--addi
        X"20210001",--addi
        X"08000004",--j
        X"AC040008",--sw
        others=>X"00000000");   


signal DO: std_logic_vector(31 downto 0);


--ifetch
signal jumpAddress : std_logic_vector(31 downto 0);
signal branchAddress:std_logic_vector(31 downto 0);
signal enable: std_logic:='0';
signal sel_Jump:std_logic;
signal PCSrc:std_logic;
signal instruction_if: std_logic_vector(31 downto 0);
signal addrUrmatoare: std_logic_vector(31 downto 0);

--instruction decode
signal instruction_id: std_logic_vector(25 downto 0); 
signal RD1: std_logic_vector(31 downto 0);
signal RD2: std_logic_vector(31 downto 0);
signal ExtImm:std_logic_vector(31 downto 0);
signal func:std_logic_vector(5 downto 0);
signal sa:std_logic_vector(4 downto 0);
signal WD:std_logic_vector(31 downto 0);
signal ExtOp: std_logic;
signal RegWrite: std_logic;
signal RegDst: std_logic;
signal rt:std_logic_vector(4 downto 0);
signal rd:std_logic_vector(4 downto 0);

--EX
signal Zero:std_logic;
signal GtZ: std_logic;
signal aluRes_ex: std_logic_vector(31 downto 0);
signal aluRes_ex2: std_logic_vector(31 downto 0);
signal wa: std_logic_vector(4 downto 0);

--MEM
signal MemData:std_logic_vector(31 downto 0);
signal aluRes_mem: std_logic_vector(31 downto 0);


--UC
signal ALUSrc: std_logic;
signal Branch: std_logic;
signal Jump: std_logic;
signal ALUOp: std_logic_vector(2 downto 0);
signal MemWrite: std_logic;
signal MemtoReg: std_logic;
signal BranchNE: std_logic;
signal BranchGtZ: std_logic;
signal instruction_uc:std_logic_vector(5 downto 0);

--pipeline registers
--IF_ID
signal PCinc_IF_ID, Instruction_IF_ID: std_logic_vector(31 downto 0);
--ID_EX
signal PCinc_ID_EX, RD1_ID_EX, RD2_ID_EX, Ext_imm_ID_EX: STD_LOGIC_VECTOR(31 downto 0);
signal func_ID_EX:STD_LOGIC_VECTOR(5 downto 0);
signal sa_ID_EX, rd_ID_EX, rt_ID_EX :STD_LOGIC_VECTOR(4  downto 0);
signal ALUOp_ID_EX: STD_LOGIC_VECTOR(2 downto 0);
signal MemtoReg_ID_EX, RegWrite_ID_EX, MemWrite_ID_EX, Branch_ID_EX, BranchNE_ID_EX, BranchGTZ_ID_EX, ALUSrc_ID_EX, RegDst_ID_EX:STD_LOGIC;
--EX_MEM
signal BranchAddress_EX_MEM, ALURes_EX_MEM, RD2_EX_MEM: STD_LOGIC_VECTOR(31 downto 0);
signal RDRT_EX_MEM:STD_LOGIC_VECTOR(4 downto 0);
signal Zero_EX_MEM, GTZ_EX_MEM, MemtoReg_EX_MEM, RegWrite_EX_MEM, MemWrite_EX_MEM, Branch_EX_MEM, BranchNE_EX_MEM, BranchGTZ_EX_MEM:STD_LOGIC;
--MEM_WB
signal MemData_MEM_WB, ALURes_MEM_WB: STD_LOGIC_VECTOR(31 downto 0);
signal RDRT_MEM_WB:STD_LOGIC_VECTOR(4 downto 0);
signal MemtoReg_MEM_WB, RegWrite_MEM_WB: STD_LOGIC;

component MPG
    Port ( btn : in STD_LOGIC;
           clk : in STD_LOGIC;
           en : out STD_LOGIC);
end component;

component SSD
    Port ( clk  : in  STD_LOGIC;
           Rst  : in  STD_LOGIC;
           Digit : in  STD_LOGIC_VECTOR (31 downto 0);   -- datele pentru 8 cifre (cifra 1 din stanga: biti 31..28)
           an   : out STD_LOGIC_VECTOR (7 downto 0);    -- selectia anodului activ
           cat  : out STD_LOGIC_VECTOR (6 downto 0));   -- selectia catozilor (segmentelor) cifrei active
end component;

component IFetch
    Port (enable: in std_logic;
          reset: in std_logic;
          clk: in std_logic;
          jumpAdress: in std_logic_vector(31 downto 0);
          branchAdress: in std_logic_vector(31 downto 0);
          selJump: in std_logic;
          selBranch: in std_logic;
          instruction: out std_logic_vector(31 downto 0);
          addrUrmatoare: out std_logic_vector(31 downto 0)
          );
end component;

component ID
Port ( Instr: in std_logic_vector(25 downto 0);
       WD : in std_logic_vector(31 downto 0);
       RegWrite: in std_logic ;
       RegDst : in std_logic ;
       ExtOp: in std_logic;
       clk: in std_logic;
       RD1: out std_logic_vector(31 downto 0);
       RD2: out std_logic_vector(31 downto 0);
       ExtImm: out std_logic_vector(31 downto 0);
       func: out std_logic_vector(5 downto 0);
       sa: out std_logic_vector(4 downto 0 );
       rt:out std_logic_vector(4 downto 0);
       rd:out std_logic_vector(4 downto 0);
       writeAddress: in std_logic_vector(4 downto 0);
       enable: in std_logic );
end component;

component UC
Port ( Instr: in std_logic_vector(5 downto 0);
       RegDst: out std_logic;
       ExtOp: out std_logic;
       ALUSrc: out std_logic;
       Branch: out std_logic;
       Jump: out std_logic;
       ALUOp: out std_logic_vector(2 downto 0);
       MemWrite: out std_logic;
       MemtoReg: out std_logic;
       RegWrite: out std_logic;
       BranchNE: out std_logic;
       BranchGtZ: out std_logic;
       clk: in std_logic );
end component;

component EX 
Port (RD1: in std_logic_vector(31 downto 0);
      RD2: in std_logic_vector(31 downto 0);
      Ext_imm: in std_logic_vector(31 downto 0);
      sa: in std_logic_vector(4 downto 0);
      func: in std_logic_vector(5 downto 0);
      PC_4: in std_logic_vector(31 downto 0);
      ALUSrc: in std_logic ;
      ALUOp: in std_logic_vector(2 downto 0);
      clk: in std_logic ;
      ALURes: out std_logic_vector(31 downto 0);
      BranchAddress: out std_logic_vector(31 downto 0);
      Zero: out std_logic;
      GtZ: out std_logic;
      rt:in  std_logic_vector(4 downto 0);
      rd:in std_logic_vector(4 downto 0);
      wa:out std_logic_vector(4 downto 0);
      RegDst: in std_logic  );
end component;

component MEM 
Port (clk : in std_logic; 
      memWrite : in std_logic; 
      ALUResIN : in std_logic_vector(31 downto 0); 
      RD2_writeData : in std_logic_vector(31 downto 0); 
      memData : out std_logic_vector(31 downto 0);
      ALUResOUT: out std_logic_vector(31 downto 0);
      enable: in std_logic );
end component ;

begin

process(clk)
begin
    case sw(7 downto 5) is
        when "000" => DO<=instruction_if;
        when "001" => DO<=addrUrmatoare;
        when "010" => DO<=RD1_ID_EX;
        when "011" => DO<=RD2_ID_EX;
        when "100" => DO<=Ext_Imm_ID_EX;
        when "101" => DO<=aluRes_ex;
        when "110" => DO<=MemData;
        when others => DO<=WD;
     end case;
end process;


sel_Jump<=Jump;--din UC
instruction_id<=instruction_if(25 downto 0);
instruction_uc<=instruction_if(31 downto 26);

--Branch Control
PCSrc<=(Zero_EX_MEM and Branch_EX_MEM) or (not Zero_EX_MEM and BranchNE_EX_MEM) or (GtZ_EX_MEM and BranchGtZ_EX_MEM);

--Write Back
WD<=MemData_MEM_WB when MemtoReg_MEM_WB='1' else ALURes_MEM_WB;

--Adresa de Jump
jumpAddress <= "000000" & Instruction_IF_ID(25 downto 0);  -- 26-bit target, no shift

--Pipeline registers

process(clk)
begin
    if rising_edge(clk) then
        if enable = '1' then
            --IF_ID
            PCinc_IF_ID<=addrUrmatoare;
            Instruction_IF_ID<=instruction_if;
            --ID_EX
            PCinc_ID_EX<=PCinc_IF_ID;
            RD1_ID_EX<=RD1;
            RD2_ID_EX<=RD2;
            Ext_imm_ID_EX<=ExtImm;
            sa_ID_EX<=sa;
            func_ID_EX<=func;
            rt_ID_EX<=rt;
            rd_ID_EX<=rd;
            
            MemtoReg_ID_EX<=MemtoReg;
            RegWrite_ID_EX<=RegWrite;
            MemWrite_ID_EX<=MemWrite;
            Branch_ID_EX<=Branch;
            BranchNE_ID_EX<=BranchNE;
            BranchGTZ_ID_EX<=BranchGTZ;
            ALUSrc_ID_EX<=ALUSrc;
            ALUOP_ID_EX<=ALUOp;
            RegDst_ID_EX<=RegDst;
            --EX_MEM
            BranchAddress_EX_MEM<=branchAddress;
            Zero_EX_MEM<=Zero;
            GTZ_EX_MEM<=GTZ;
            ALURes_EX_MEM<=aluRes_ex;
            RD2_EX_MEM<=RD2_ID_EX;
            RDRT_EX_MEM<=wa;
            
            MemtoReg_EX_MEM<=MemtoReg_ID_EX;
            RegWrite_EX_MEM<=RegWrite_ID_EX;
            MemWrite_EX_MEM<=MemWrite_ID_EX;
            Branch_EX_MEM<=Branch_ID_EX;
            BranchNE_EX_MEM<=BranchNE_ID_EX;
            BranchGTZ_EX_MEM<=BranchGTZ_ID_EX;
            --MEM_WB
            MemData_MEM_WB<=MemData;
            ALURes_MEM_WB<=aluRes_mem;
            RDRT_MEM_WB<= RDRT_EX_MEM;
            
            MemtoReg_MEM_WB<=MemtoReg_EX_MEM;
            RegWrite_MEM_WB<=RegWrite_EX_MEM;
            
        end if;
    end if;
end process;



led(0)<=RegDst;
led(1)<=ExtOp;
led(2)<=ALUSrc;
led(3)<=Branch;
led(4)<=Jump;
led(7 downto 5)<=ALUOp(2 downto 0);
led(8)<=MemWrite;
led(9)<=MemtoReg;
led(10)<=RegWrite;
led(11)<=BranchNE;
led(12)<=BranchGtZ;
led(13)<=PCSrc;
led(14)<=Zero;


C1: MPG port map (btn=>btn,
                  clk=>clk,
                  en=>enable);
                  
I: IFetch port map(enable=>enable,
                   reset=>btnReset,
                   clk=>clk,
                   jumpAdress=>jumpAddress,
                   branchAdress=>branchAddress_EX_MEM,
                   selJump=>sel_Jump,
                   selBranch=>PCSrc,
                   instruction=>instruction_if,
                   addrUrmatoare=>addrUrmatoare);
                   
S: SSD port map(clk=>clk,
                rst=>sw(2),
                Digit=>DO,
                an=>an,
                cat=>cat);
                
I_D: ID port map(Instr=>Instruction_IF_ID(25 downto 0),
                 WD=>WD,
                 RegWrite=>RegWrite_MEM_WB,
                 RegDst=>RegDst,
                 ExtOp=>ExtOp,
                 clk=>clk,
                 RD1=>RD1,
                 RD2=>RD2,
                 ExtImm=>ExtImm,
                 func=>func,
                 sa=>sa,
                 rd=>rd,
                 rt=>rt,
                 writeAddress=>RDRT_MEM_WB,
                 enable=>enable);
                 
                 
U: UC port map(Instr=>instruction_IF_ID(31 downto 26),
               RegDst=>RegDst,
               ExtOp=>ExtOp,
               ALUSrc=>ALUSrc,
               Branch=>Branch,
               Jump=>Jump,
               ALUOp=>ALUOp(2 downto 0),
               MemWrite=>MemWrite,
               MemtoReg=>MemtoReg,
               RegWrite=>RegWrite,
               BranchNE=>BranchNE,
               BranchGtZ=>BranchGtZ,
               clk=>clk);
               
E: EX port map(RD1=>RD1_ID_EX,
               RD2=>RD2_ID_EX,
               Ext_imm=>Ext_imm_ID_EX,
               sa=>sa_ID_EX,
               func=>func_ID_EX,
               PC_4=>PCinc_ID_EX,
               ALUSrc=>ALUSrc_ID_EX,
               ALUOp=>ALUOp_ID_EX(2 downto 0),
               clk=>clk,
               ALURes=>aluRes_ex,
               BranchAddress=>branchAddress,
               Zero=>Zero,
               GtZ=>GtZ,
               rt=>rt_ID_EX,
               rd=>rd_ID_EX,
               wa=>wa,    --RD SAU RT
               RegDst=>RegDst_ID_EX); 
M: MEM port map(clk=>clk,
                memWrite=>MemWrite_EX_MEM,
                ALUResIN=>ALURes_EX_MEM,
                RD2_writeData=>RD2_EX_MEM,
                memData=>MemData,
                ALUResOUT=>aluRes_mem,
                enable=>enable);

end Behavioral;
