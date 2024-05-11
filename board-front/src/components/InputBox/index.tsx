import React, { forwardRef, ChangeEvent , KeyboardEvent} from 'react'
import './style.css';


interface Props {
    label: string;
    type: 'text' | 'password';
    placeholder : string;
    onChange: (event: ChangeEvent<HTMLInputElement>) => void;
    value : string;
    error: boolean;

    icon?: 'eye-light-off-icon' | 'eye-light-on-icon' | 'expand-right-light-icon';
    onButtonClick?: () => void;

    message?: string;

    onKeyDown?: (event: KeyboardEvent<HTMLInputElement>) => void;
}

//             component : Input Box           //

const InputBox = forwardRef<HTMLInputElement, Props>((props: Props, ref) => {
//              state : properties            //
    const {label, type,  placeholder , value, error, icon, message } = props;
    const{onChange, onButtonClick, onKeyDown} = props;

// onchange event handler 
    const onKeyDownHandler = (event: KeyboardEvent<HTMLInputElement> ) => {
        if(!onKeyDown) return;
        onKeyDown(event);
    };
    
//              render : Input Box 컴포넌트   //    

    return (
        <div className='inputbox'>
            <div className='inputbox-label'>{label}</div>
            <div className={error ? 'inputbox-container-error' : 'inputbox-container'}>
                <input ref={ref} type={type} className='input' placeholder={placeholder} value={value} onChange={onChange} onKeyDown={onKeyDown}/>
                { onButtonClick !== undefined &&  
                    <div className='icon-button' onClick={onButtonClick}>
                        {icon !== undefined && 
                         <div className={`icon ${icon}`}></div> }
                </div>
                }
            </div>
            {message !== undefined && 
            <div className='inputbox-message'>{message}</div>}
        </div>

    )


});

export default InputBox;