package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase1 {

    /* Translates the MAL instruction to 1-3 TAL instructions
     * and returns the TAL instructions in a list
     *
     * mals: input program as a list of Instruction objects
     *
     * returns a list of TAL instructions (should be same size or longer than input list)
     */
    public static List<Instruction> mal_to_tal(List<Instruction> mals) {
        List<Instruction> talList = new LinkedList<Instruction>();
        Instruction talInstruction;
        for(int i = 0; i < mals.size(); i++){
            if(mals.get(i).instruction_id.equals(Instruction.ID.addu)){
                talInstruction = mals.get(i).copy();
                talList.add(talInstruction);
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.beq)){
                talInstruction = mals.get(i).copy();
                talList.add(talInstruction);
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.bne)){
                talInstruction = mals.get(i).copy();
                talList.add(talInstruction);
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.or)){
                talInstruction = mals.get(i).copy();
                talList.add(talInstruction);
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.lui)){
                talInstruction = mals.get(i).copy();
                talList.add(talInstruction);
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.slt)){
                talInstruction = mals.get(i).copy();
                talList.add(talInstruction);
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.ori)){
                if(mals.get(i).immediate <= 0x0000FFFF){
                    talInstruction = mals.get(i).copy();
                    talList.add(talInstruction);
                }
                else{
                    String hexString = Integer.toHexString(mals.get(i).immediate);
                    String lowerString = hexString.substring(hexString.length()-4);
                    String upperString = hexString.substring(0, hexString.length()-4);
                    int lower = Integer.decode("0x" + lowerString);
                    int upper = Integer.decode("0x" + upperString);

                    talInstruction = InstructionFactory.CreateLui(1, upper);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateOri(1, 1, lower);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateOr(mals.get(i).rd, mals.get(i).rs, 1);
                    talList.add(talInstruction);
                }
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.addiu)){
                if(mals.get(i).immediate <= 0x0000FFFF){
                  talInstruction = mals.get(i).copy();
                  talList.add(talInstruction);
                }else{
                    //get the upper and lower 16 bits of the immediate
                    String hexString = Integer.toHexString(mals.get(i).immediate);
                    String lowerString = hexString.substring(hexString.length()-4);
                    String upperString = hexString.substring(0, hexString.length()-4);
                    int lower = Integer.decode("0x" + lowerString);
                    int upper = Integer.decode("0x" + upperString);

                    //create the instructions with the upper and lower halves of the immediate
                    talInstruction = InstructionFactory.CreateLui(1, upper);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateOri(1, 1, lower);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateAddu(mals.get(i).rt, mals.get(i).rs, 1);
                    talList.add(talInstruction);
                }
            }
            if(mals.get(i).instruction_id.equals(Instruction.ID.blt)){
                if(mals.get(i).branch_label != null){
                    talInstruction = InstructionFactory.CreateSlt(1, mals.get(i).rt, mals.get(i).rs);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateBne(1, 0, mals.get(i).branch_label);
                    talList.add(talInstruction);
                }else{
                    talInstruction = InstructionFactory.CreateSlt(1, mals.get(i).rt, mals.get(i).rs);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateBne(1, 0, mals.get(i).immediate);
                    talList.add(talInstruction);
                }
            }

            if(mals.get(i).instruction_id.equals(Instruction.ID.bge)){
                if(mals.get(i).branch_label != null){
                    talInstruction = InstructionFactory.CreateSlt(1, mals.get(i).rt, mals.get(i).rs);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateBeq(1, 0, mals.get(i).branch_label);
                    talList.add(talInstruction);
                }else{
                    talInstruction = InstructionFactory.CreateSlt(1, mals.get(i).rt, mals.get(i).rs);
                    talList.add(talInstruction);
                    talInstruction = InstructionFactory.CreateBeq(1, 0, mals.get(i).immediate);
                    talList.add(talInstruction);
                }
            }
        }

        return talList;
    }
}
