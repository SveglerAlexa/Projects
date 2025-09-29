----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/25/2025 07:26:49 PM
-- Design Name: 
-- Module Name: IFetch - Behavioral
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

entity IFetch is
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
end IFetch;

architecture Behavioral of IFetch is

type mem_rom is array(0 to 31) of std_logic_vector(31 downto 0);
signal ROM: mem_rom :=( 
        X"00000820",--add
        X"8C020000",--lw
        X"20030001",--addi
        X"00002020",--add
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
        X"AC040008",--SW
        X"8C090008",--LW
        others=>X"00000000");  
        
signal PC: std_logic_vector(31 downto 0);
signal PCinCuv: std_logic_vector(31 downto 0);
signal adresaMem: std_logic_vector(4 downto 0);
signal iesireMux1: std_logic_vector(31 downto 0);
signal intrarePC:  std_logic_vector(31 downto 0);
begin

process(clk)
    begin
        if reset = '1' then
            pc <= x"00000000";
        else if rising_edge(clk) then
                if enable = '1' then
                     PC<=intrarePC;
                end if;
             end if;
        end if;
    end process;
    
 
 adresaMem<=PC(4 downto 0);
 
 instruction <= ROM(conv_integer(adresaMem));
    
 iesireMux1<=PC+1 when selBranch='0' else branchAdress;
 
 intrarePC<=iesireMux1 when selJump='0' else jumpAdress;

 addrUrmatoare<=PC+1;

 
end Behavioral;
