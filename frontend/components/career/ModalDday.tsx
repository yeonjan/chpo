import { useState } from 'react';
import { InputDiv } from './NewCareer';
import DatePicker from './DatePicker';
import styled from 'styled-components';
import CancelOk from '../common/CancelOk';
import { IDdayModalProps } from './ICareer';
import Swal from 'sweetalert2';

const ModalDiv = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 11;

  .darkBg {
    height: 100%;
    width: 100%;
    background-color: ${(props) => props.theme.modalGray};

    display: flex;
    align-items: end;

    .modalContent {
      width: 100%;
      background-color: white;

      .modalTitle {
        margin: 1rem;
        border-bottom: 2px solid ${(props) => props.theme.primary};
        text-align: center;
        font-size: large;
      }
    }
  }
`;

const DdayModal = ({ close, result, defaultDate }: IDdayModalProps) => {
  const [ddayName, setDdayName] = useState<string>('');
  const [date, setDate] = useState<string>('');

  const updateDdayName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    if (value.length > 10) {
    } else {
      setDdayName(e.target.value);
    }
  };

  const updateDate = (date: string) => {
    setDate(date);
  };

  const ok = () => {
    if (ddayName.length === 0) {
      Swal.fire({
        text: '다음 일정 이름을 입력해주세요',
        icon: 'info',
      });
    } else {
      result({ ddayName, date });
      close();
    }
  };

  return (
    <ModalDiv>
      <div className="darkBg" onClick={close}>
        <div className="modalContent">
          <div className="modalTitle">다음일정 변경하기</div>
          <InputDiv>
            <div>다음일정 (최대 10글자)</div>
            <input
              type="text"
              className="input"
              placeholder="ex) 코딩테스트, 1차 면접"
              value={ddayName}
              onChange={updateDdayName}
            ></input>
          </InputDiv>
          <DatePicker updateDate={updateDate} defaultDate={defaultDate}></DatePicker>
          <CancelOk cancel={close} ok={ok}></CancelOk>
        </div>
      </div>
    </ModalDiv>
  );
};

export default DdayModal;
