package pt.ist.processpedia.dto;

public class ProcessTagDto extends TagDto {

    private LabelDto label;
    private ProcessDto process;

    public ProcessTagDto(String id, LabelDto label, ProcessDto process) {
        super(id, label);
        this.process = process;
    }

    public LabelDto getLabel() {
        return label;
    }
    
    public ProcessDto getProcess() {
        return process;
    }
    
}