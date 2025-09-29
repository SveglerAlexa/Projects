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

--EX
signal Zero:std_logic;
signal GtZ: std_logic;
signal aluRes_ex: std_logic_vector(31 downto 0);

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
      GtZ: out std_logic  );
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
        when "010" => DO<=RD1;
        when "011" => DO<=RD2;
        when "100" => DO<=ExtImm;
        when "101" => DO<=aluRes_ex;
        when "110" => DO<=MemData;
        when others => DO<=WD;
     end case;
end process;


sel_Jump<=Jump;--din UC
instruction_id<=instruction_if(25 downto 0);
instruction_uc<=instruction_if(31 downto 26);
PCSrc<=(Zero and Branch) or (not Zero and BranchNE) or (GtZ and BranchGtZ);
WD<=MemData when MemtoReg='1' else aluRes_ex;
jumpAddress <= "000000" & instruction_if(25 downto 0);  -- 26-bit target, no shift

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
                   branchAdress=>branchAddress,
                   selJump=>sel_Jump,
                   selBranch=>PCSrc,
                   instruction=>instruction_if,
                   addrUrmatoare=>addrUrmatoare);
                   
S: SSD port map(clk=>clk,
                rst=>sw(2),
                Digit=>DO,
                an=>an,
                cat=>cat);
                
I_D: ID port map(Instr=>instruction_id,
                 WD=>WD,
                 RegWrite=>RegWrite,
                 RegDst=>RegDst,
                 ExtOp=>ExtOp,
                 clk=>clk,
                 RD1=>RD1,
                 RD2=>RD2,
                 ExtImm=>ExtImm,
                 func=>func,
                 sa=>sa,
                 enable=>enable);
                 
                 
U: UC port map(Instr=>instruction_uc,
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
               
E: EX port map(RD1=>RD1,
               RD2=>RD2,
               Ext_imm=>ExtImm,
               sa=>sa,
               func=>func,
               PC_4=>addrUrmatoare,
               ALUSrc=>ALUSrc,
               ALUOp=>ALUOp(2 downto 0),
               clk=>clk,
               ALURes=>aluRes_ex,
               BranchAddress=>branchAddress,
               Zero=>Zero,
               GtZ=>GtZ); 
M: MEM port map(clk=>clk,
                memWrite=>MemWrite,
                ALUResIN=>aluRes_ex,
                RD2_writeData=>RD2,
                memData=>MemData,
                ALUResOUT=>aluRes_ex,
                enable=>enable);

end Behavioral;
