----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/02/2025 01:35:45 AM
-- Design Name: 
-- Module Name: UC - Behavioral
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

entity UC is
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
end UC;

architecture Behavioral of UC is

begin

process(clk, Instr)
    begin
    
    RegDst <= '0';
    ExtOp <= '0';
    ALUSrc <= '0';
    Branch <= '0';
    Jump <= '0';
    ALUOp <= "000";
    MemWrite <= '0';
    MemtoReg <= '0';
    RegWrite <= '0';
    BranchNE<= '0';
    BranchGtZ<='0';
        case Instr is
            when "000000" => RegDst <= '1';RegWrite <= '1';ALUOp<="010"; --tip R
            when "001100" => RegWrite<='1';ALUSrc<='1';ALUOp<="001";--andi
            when "001000" => RegWrite<='1';ALUSrc<='1';ExtOp<='1';ALUOp<="100";--addi
            when "100011" => RegWrite<='1';ALUSrc<='1';ExtOp<='1';MemtoReg<='1';ALUOp<="100";--lw
            when "101011" => ALUSrc<='1';ExtOp<='1';MemWrite<='1';ALUOp<="100";--sw
            when "000100" => ExtOp<='1'; Branch<='1';ALUOp<="011";--beq
            when "000101" => ExtOp<='1'; Branch<='0';ALUOp<="011";BranchNE<='1';--bne
            when "000001" => ExtOp<='1'; Branch<='0';ALUOp<="011";BranchGtZ<='1';--bgez
            when "000010"  => Jump<='1';--j
            when others => null;
         end case;
 end process;
    

end Behavioral;
