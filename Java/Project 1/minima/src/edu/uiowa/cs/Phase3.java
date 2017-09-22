package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase3 {

    /* Translate each Instruction object into
     * a 32-bit number.
     *
     * tals: list of Instructions to translate
     *
     * returns a list of instructions in their 32-bit binary representation
     *
     */
    public static List<Integer> translate_instructions(List<Instruction> tals) {
        List<Integer> returnList = new LinkedList<>();
        int addu_opcode = 33, or_opcode = 37, slt_opcode = 42, beq_opcode = 4, bne_opcode = 5, addiu_opcode = 9, ori_opcode = 13, lui_opcode = 15;
        int instr_int, instr_rs, instr_rt, instr_rd, instr_funct, instr_imm = 0;
        for(int i = 0; i < tals.size(); i++){
            if(tals.get(i).instruction_id.equals(Instruction.ID.addu) || tals.get(i).instruction_id.equals(Instruction.ID.or) || tals.get(i).instruction_id.equals(Instruction.ID.slt)){
                //R-TYPE INSTRUCTIONS
                //find the appropriate opcode, add it to the returned instruction
                if(tals.get(i).instruction_id.equals(Instruction.ID.addu)) instr_int = addu_opcode;
                else if(tals.get(i).instruction_id.equals(Instruction.ID.or)) instr_int = or_opcode;
                else instr_int = slt_opcode;

                  //put the rs register in the appropriate spot
                  instr_rs = tals.get(i).rs;
                  instr_rs = instr_rs << 21;
                  instr_int += instr_rs;

                  //put the rt register in the appropriate spot
                  instr_rt = tals.get(i).rt;
                  instr_rt = instr_rt << 16;
                  instr_int += instr_rt;

                  //put the rd register in appropriate spot
                  instr_rd = tals.get(i).rd;
                  instr_rd = instr_rd << 11;
                  instr_int += instr_rd;

                  returnList.add(instr_int);

            }else{
                //I-TYPE INSTRUCTIONS
                instr_int = 0;

                //put the opcode in the appropriate spot
                //System.out.println("beq_opcode << 23 = " + Integer.toBinaryString((beq_opcode << 26)));
                if(tals.get(i).instruction_id.equals(Instruction.ID.beq)) instr_int += (beq_opcode << 26);
                else if(tals.get(i).instruction_id.equals(Instruction.ID.bne)) instr_int += (bne_opcode << 26);
                else if(tals.get(i).instruction_id.equals(Instruction.ID.addiu)) instr_int += (addiu_opcode << 26);
                else if(tals.get(i).instruction_id.equals(Instruction.ID.ori)) instr_int += (ori_opcode << 26);
                else instr_int += (lui_opcode << 26);

                //put the rs register in the appropriate spot
                instr_rs = tals.get(i).rs;
                instr_rs = instr_rs << 21;
                instr_int += instr_rs;

                //put the rt register in the appropriate spot
                instr_rt = tals.get(i).rt;
                instr_rt = instr_rt << 16;
                instr_int += instr_rt;

                //give the immediate to the (potentially) first 16 bits
                instr_imm = tals.get(i).immediate;
                String hexString = Integer.toHexString(instr_imm);
                if (hexString.length()>4){
                  String newhex = hexString.substring(hexString.length()-4);
                  instr_imm = Integer.decode("0x" + newhex);
                }
                instr_int += instr_imm;
                returnList.add(instr_int);
            }
        }
        return returnList;
    }
}
